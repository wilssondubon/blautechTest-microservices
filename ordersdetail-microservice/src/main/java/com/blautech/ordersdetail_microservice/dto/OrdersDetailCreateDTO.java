package com.blautech.ordersdetail_microservice.dto;

import lombok.*;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDetailCreateDTO {
    @NotBlank(message = "orderId is required")
    private Integer orderId;
    @NotBlank(message = "productId is required")
    private Integer productId;
    @NotBlank(message = "quantity is required")
    private Integer quantity;
    @NotBlank(message = "subtotal is required")
    private BigDecimal subtotal;
}