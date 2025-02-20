package com.blautech.users_microservice.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String email;
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
    @NotBlank(message = "Date of birth is required")
    private Date dateOfBirth;
}
