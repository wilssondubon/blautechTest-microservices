package com.blautech.auth_microservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutheticationResponseDTO {
    public String token;
    public Integer userId;
    public String email;
    public String message;
    public boolean success;
}
