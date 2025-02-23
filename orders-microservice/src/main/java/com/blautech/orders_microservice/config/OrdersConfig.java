package com.blautech.orders_microservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OrdersConfig {

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

}