package com.blautech.cart_microservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartConfig {

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}
