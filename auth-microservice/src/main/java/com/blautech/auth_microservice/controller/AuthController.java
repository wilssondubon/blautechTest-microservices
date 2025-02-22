package com.blautech.auth_microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.blautech.auth_microservice.dto.UserLoginCredentialsDTO;
import com.blautech.auth_microservice.dto.UserRegistryDTO;
import com.blautech.auth_microservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserRegistryDTO user){
        return authService.registerUser(user);
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