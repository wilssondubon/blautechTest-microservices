package com.blautech.cart_microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {
    public static final String REQUEST_QUEUE = "product.request";
    public static final String RESPONSE_QUEUE = "product.response";

    @Bean
    public Queue requestQueue() {
        return new Queue(REQUEST_QUEUE, false);
    }

    @Bean
    public Queue responseQueue() {
        return new Queue(RESPONSE_QUEUE, false);
    } 
}
