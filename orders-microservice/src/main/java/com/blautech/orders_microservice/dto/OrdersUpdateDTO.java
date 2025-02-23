package com.blautech.orders_microservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersUpdateDTO {
    private Integer userId;
    private BigDecimal totalPrice;
    private Timestamp orderDate;
    private List<OrdersDetailUpdateDTO> orderDetails;
}