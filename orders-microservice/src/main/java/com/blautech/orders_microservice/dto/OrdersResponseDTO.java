package com.blautech.orders_microservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.blautech.orders_microservice.entity.Orders;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDTO {
    private int id;
    private Integer userId;
    private BigDecimal totalPrice;
    private Timestamp orderDate;
    private List<OrdersDetailResponseDTO> orderDetails;
    
    public OrdersResponseDTO(Orders order, List<OrdersDetailResponseDTO> details){
        this.id = order.getId();
        this.userId  = order.getUserId();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.orderDetails = details;
    }
}