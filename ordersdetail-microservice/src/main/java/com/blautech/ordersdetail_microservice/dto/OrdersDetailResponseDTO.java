package com.blautech.ordersdetail_microservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDetailResponseDTO {
    private Integer id;
    private Integer orderId;
    private ProductsResponseDTO product;
    private Integer quantity;
    private BigDecimal subtotal;
}