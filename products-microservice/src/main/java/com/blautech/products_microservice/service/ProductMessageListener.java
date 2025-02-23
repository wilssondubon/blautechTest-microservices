package com.blautech.products_microservice.service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blautech.products_microservice.config.RabbitMQConfig;
import com.blautech.products_microservice.dto.ProductsResponseDTO;

@Service
public class ProductMessageListener {

    @Autowired
    private ProductsService productsService;

    
    @RabbitListener(queues = RabbitMQConfig.REQUEST_QUEUE)
    // @SendTo(RabbitMQConfig.RESPONSE_QUEUE)
    public List<ProductsResponseDTO> getProductos(List<Integer> productsId) {
        return productsService.getProductsByIds(productsId);
    }

}
