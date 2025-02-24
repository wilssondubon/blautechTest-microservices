package com.blautech.orders_microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.blautech.orders_microservice.config.FeignClientConfig;
import com.blautech.orders_microservice.dto.CartResponseDTO;

import java.util.List;

@FeignClient(name = "cart-service", url = "http://localhost:8080/api/cart", configuration = FeignClientConfig.class)
public interface CartClient {

    @GetMapping("/user/{userId}")
    List<CartResponseDTO> getCartByUserId(@PathVariable Integer userId);

    @DeleteMapping("/user/{userId}")
    void deleteCartByUserId(@PathVariable Integer userId);
}