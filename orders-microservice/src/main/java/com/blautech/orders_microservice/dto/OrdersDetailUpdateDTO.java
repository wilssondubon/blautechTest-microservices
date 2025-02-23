package com.blautech.orders_microservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDetailUpdateDTO {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal subtotal;
}