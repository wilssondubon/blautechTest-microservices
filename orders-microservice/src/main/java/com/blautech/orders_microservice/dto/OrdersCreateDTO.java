package com.blautech.orders_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersCreateDTO {
    private Integer userId;
    private BigDecimal totalPrice;
    private Timestamp orderDate;
    private List<OrdersDetailCreateDTO> orderDetails;
}