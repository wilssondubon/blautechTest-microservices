package com.blautech.users_microservice.config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersConfig {


    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

}