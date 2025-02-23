package com.blautech.ordersdetail_microservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ordersdetail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="order_id", nullable = false)
    private Integer orderId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal subtotal;
}

