package com.blautech.auth_microservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDTO {
    private int id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Date dateOfBirth;
}
