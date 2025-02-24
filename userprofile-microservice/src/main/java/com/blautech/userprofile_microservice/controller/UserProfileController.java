package com.blautech.userprofile_microservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.blautech.userprofile_microservice.dto.UserProfileCreateDTO;
import com.blautech.userprofile_microservice.dto.UserProfileResponseDTO;
import com.blautech.userprofile_microservice.dto.UserProfileUpdateDTO;
import com.blautech.userprofile_microservice.service.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;


@RestController
@RequestMapping("/api/userprofile")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getUserById(@PathVariable int id) {
        UserProfileResponseDTO userProfile = userProfileService.getUserProfileByUserId(id);
        if (userProfile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userProfile);
    }


    @PostMapping("/save")
    public ResponseEntity<UserProfileResponseDTO> save(@RequestBody @Validated UserProfileCreateDTO userProfile) {
        UserProfileResponseDTO userProfileNew = userProfileService.saveUserProfile(userProfile);
        return ResponseEntity.created(URI.create("/" + userProfileNew.getId()))
        .body(userProfileNew);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserProfileResponseDTO> updateProfile(@PathVariable Integer id, @RequestBody @Validated UserProfileUpdateDTO userProfile) {
        
        UserProfileResponseDTO userProfileUpdate = userProfileService.updateUserProfile(id, userProfile);
        if (userProfileUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(userProfileUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean isDeleted = userProfileService.deleteUserProfileById(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
