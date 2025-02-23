package com.blautech.orders_microservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

import com.blautech.orders_microservice.entity.OrdersDetail;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDetailResponseDTO {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private BigDecimal subtotal;
    private ProductsResponseDTO product;

    public OrdersDetailResponseDTO(OrdersDetail detail, ProductsResponseDTO product){
        this.id = detail.getId();;
        this.productId = detail.getProductId();
        this.product = product;
        this.quantity = detail.getQuantity();
        this.subtotal = detail.getSubtotal();
    }
}