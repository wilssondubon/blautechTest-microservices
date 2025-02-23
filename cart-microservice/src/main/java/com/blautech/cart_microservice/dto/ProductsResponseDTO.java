package com.blautech.cart_microservice.dto;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductsResponseDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
}
