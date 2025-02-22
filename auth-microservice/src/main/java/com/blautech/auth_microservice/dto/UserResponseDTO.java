package com.blautech.auth_microservice.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String shippingAddress;
    private Date dateOfBirth;
}
