package com.blautech.auth_microservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedResponseDTO {
    private boolean success;
    private String message;
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Date dateOfBirth;

    public UserCreatedResponseDTO(boolean pSuccess, String pMessage){
        success = pSuccess;
        message = pMessage;
    }
}
