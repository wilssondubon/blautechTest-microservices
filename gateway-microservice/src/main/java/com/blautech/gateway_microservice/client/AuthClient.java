package com.blautech.gateway_microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service", url = "http://localhost:8080/api/auth")
public interface AuthClient {

    @GetMapping("/validate")
    String validateToken(@RequestParam String token);
    
}
