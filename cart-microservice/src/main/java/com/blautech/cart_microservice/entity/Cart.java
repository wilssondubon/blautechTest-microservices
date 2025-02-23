package com.blautech.cart_microservice.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer user_id;

    private Integer product_id;

    private Integer quantity;

    private BigDecimal subtotal;
}
