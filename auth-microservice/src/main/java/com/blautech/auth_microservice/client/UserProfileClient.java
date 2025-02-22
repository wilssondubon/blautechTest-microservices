package com.blautech.auth_microservice.client;

import com.blautech.auth_microservice.dto.UserProfileCreateDTO;
import com.blautech.auth_microservice.dto.UserProfileResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userprofile-service", url = "http://localhost:8080/api/userprofile")
public interface UserProfileClient {

    @PostMapping("/save")
    UserProfileResponseDTO saveUser(@RequestBody UserProfileCreateDTO userCreateDTO);
    

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable Integer id);
}