package com.blautech.gateway_microservice.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.blautech.gateway_microservice.dto.ValidTokenDTO;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter  extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    @Autowired
    private RouteValidator validator;

    @Autowired
    private WebClient.Builder webClient;

    public static class Config {}

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if (validator.isSecured.test(exchange.getRequest()))
            {
                //header contains token
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    return unauthorizedResponse(exchange, "missing authorization header");
                }
            
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try{

                    return webClient.build()
                        .get()
                        .uri("http://localhost:8080/api/auth/validate?token="+authHeader)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, response -> {
                            if (response.statusCode().equals(HttpStatus.UNAUTHORIZED)){
                                return Mono.error(new RuntimeException("Invalid Token"));
                            }
                            return response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new RuntimeException("Client Error: [" + response.statusCode() + "] " + errorBody)));
                        })
                        .bodyToMono(ValidTokenDTO.class)
                        .flatMap(t->{
                            t.getToken();
                            if (t.isValid()) {
                                return chain.filter(exchange);
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                                return WriteResponse(t.message, exchange.getResponse());
                            }
                        })
                        .onErrorResume(e-> {
                            if (e.getMessage().equals("Invalid Token")){
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                                return WriteResponse(e.getMessage(), exchange.getResponse());
                            }
                            if (e.getMessage().startsWith("Client Error: [")) {
                                String statusCode = e.getMessage().substring(14, 17);
                                exchange.getResponse().setStatusCode(HttpStatus.valueOf(Integer.parseInt(statusCode)));

                                return WriteResponse(e.getMessage(), exchange.getResponse());
                            }
                            return Mono.error(e);
                        });

                }catch (Exception e) {
                    return unauthorizedResponse(exchange, "Unauthorized");
                }
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        
        return WriteResponse(message, response);
    }


    private Mono<Void> WriteResponse(String message, ServerHttpResponse response){
        byte[] bytes = message.getBytes();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        response.getHeaders().setContentLength(bytes.length);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
    }
}
