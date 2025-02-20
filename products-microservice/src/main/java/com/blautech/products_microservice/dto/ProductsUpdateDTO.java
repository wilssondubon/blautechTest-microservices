package com.blautech.products_microservice.dto;

import lombok.*;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsUpdateDTO {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "price is required")
    private BigDecimal price;
    @NotBlank(message = "image URL is required")
    private String imageUrl;
}