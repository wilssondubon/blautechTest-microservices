package com.blautech.auth_microservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistryDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String shippingAddress;
    private Date dateOfBirth;
}
