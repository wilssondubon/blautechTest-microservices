package com.blautech.gateway_microservice.dto;

import java.util.Date;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidTokenDTO {
    public String token;
    public boolean valid;
    public Date date;
    public String message;
}
