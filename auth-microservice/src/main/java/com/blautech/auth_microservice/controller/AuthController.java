package com.blautech.auth_microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.blautech.auth_microservice.dto.UserCreatedResponseDTO;
import com.blautech.auth_microservice.dto.UserLoginCredentialsDTO;
import com.blautech.auth_microservice.dto.UserRegistryDTO;
import com.blautech.auth_microservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserCreatedResponseDTO> addNewUser(@RequestBody @Validated UserRegistryDTO user){
        UserCreatedResponseDTO userNew = authService.registerUser(user);
        return ResponseEntity.ok(userNew);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody UserLoginCredentialsDTO user) {
        return authService.generateToken(user);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        authService.validateToken(token);
        return "Token is valid";
    }


}