package com.blautech.cart_microservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateDTO {
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private BigDecimal subtotal;
}
