package com.blautech.cart_microservice.dto;

import java.math.BigDecimal;

import com.blautech.cart_microservice.entity.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Integer id;

    private Integer user_id;

    private Integer product_id;

    private ProductsResponseDTO product;

    private Integer quantity;

    private BigDecimal subtotal;

    public CartResponseDTO(Cart cart, ProductsResponseDTO product){
        this.id = cart.getId();
        this.user_id = cart.getUser_id();
        this.product_id = cart.getProduct_id();
        this.product = product;
        this.quantity = cart.getQuantity();
        this.subtotal = cart.getSubtotal();
    }
}
