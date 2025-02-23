package com.blautech.orders_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailCreateDTO {
    private Integer productId;
    private Integer quantity;
    private BigDecimal subtotal;
}