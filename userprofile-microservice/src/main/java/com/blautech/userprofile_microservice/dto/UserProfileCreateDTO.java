package com.blautech.userprofile_microservice.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileCreateDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Date dateOfBirth;
}