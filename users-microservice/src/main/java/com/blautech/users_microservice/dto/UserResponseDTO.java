package com.blautech.users_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String shippingAddress;
    private Date dateOfBirth;
}
