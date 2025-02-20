
package com.blautech.ordersdetail_microservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OrdersDetailConfig {

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

}
