package com.blautech.auth_microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;

import com.blautech.auth_microservice.config.CustomUserDetails;
import com.blautech.auth_microservice.dto.AutheticationResponseDTO;
import com.blautech.auth_microservice.dto.UserCreatedResponseDTO;
import com.blautech.auth_microservice.dto.UserLoginCredentialsDTO;
import com.blautech.auth_microservice.dto.UserRegistryDTO;
import com.blautech.auth_microservice.dto.ValidTokenDTO;
import com.blautech.auth_microservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager autheticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserCreatedResponseDTO> addNewUser(@RequestBody @Validated UserRegistryDTO user) {
        UserCreatedResponseDTO userNew = authService.registerUser(user);
        if (!userNew.isSuccess())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userNew);
        return ResponseEntity.ok(userNew);
    }

    @PostMapping("/token")
    public ResponseEntity<AutheticationResponseDTO> getToken(@RequestBody UserLoginCredentialsDTO user) {
        try {
            Authentication authenticate = autheticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if (authenticate.isAuthenticated()) {
                CustomUserDetails details = (CustomUserDetails) authenticate.getPrincipal();
                return ResponseEntity.ok(new AutheticationResponseDTO(
                    authService.generateToken(details.getId(), user),
                    details.getId(),
                    user.getEmail(),
                    "ok",
                    true
                    ));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AutheticationResponseDTO(
                    "",
                    0,
                    user.getEmail(),
                    "invalid access",
                    false
                    ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AutheticationResponseDTO(
                "",
                0,
                user.getEmail(),
                "authentication fail",
                false
                ));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ValidTokenDTO> validateToken(@RequestParam("token") String token) {
        try {
            authService.validateToken(token);
            return ResponseEntity.ok(new ValidTokenDTO(
                    token,
                    true,
                    new Date(),
                    "token is valid"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ValidTokenDTO(
                    token,
                    false,
                    new Date(),
                    "invalid token"));
        }
    }

}