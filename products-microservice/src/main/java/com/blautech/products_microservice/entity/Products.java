package com.blautech.products_microservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;

import java.math.BigDecimal; 

@Entity
@Data
@Table(name="products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Products {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name", length = 100)
    private String name;

    @Column(name="description", columnDefinition = "TEXT")
    private String description;

    @Column(name="price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name="image_url", length = 255)
    private String imageUrl;
}