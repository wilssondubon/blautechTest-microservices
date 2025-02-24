package com.blautech.orders_microservice.dto;


import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private ProductsResponseDTO product;

    private Integer quantity;

    private BigDecimal subtotal;
}

