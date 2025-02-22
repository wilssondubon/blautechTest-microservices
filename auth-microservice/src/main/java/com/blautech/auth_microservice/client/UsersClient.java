package com.blautech.auth_microservice.client;

import com.blautech.auth_microservice.dto.UserCreateDTO;
import com.blautech.auth_microservice.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "users-service", url = "http://localhost:8080/api/users")
public interface UsersClient {

    @PostMapping("/save")
    UserResponseDTO saveUser(@RequestBody UserCreateDTO userCreateDTO);
    
    @GetMapping("/by-email")
    UserResponseDTO getUserByEmail(@RequestParam String email);

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable Integer id);
}