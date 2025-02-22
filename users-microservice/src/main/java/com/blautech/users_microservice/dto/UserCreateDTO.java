package com.blautech.users_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {
    @NotBlank(message = "First name is mandatory")
    @Size(max = 100, message = "First name must be less than 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 100, message = "Last name must be less than 100 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Shipping address is mandatory")
    private String shippingAddress;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
}
