package com.blautech.users_microservice.controller;

import com.blautech.users_microservice.service.UsersService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blautech.users_microservice.dto.UserCreateDTO;
import com.blautech.users_microservice.dto.UserResponseDTO;
import com.blautech.users_microservice.dto.UserUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;



@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UsersService usersService;



    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> users = usersService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
        UserResponseDTO user = usersService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@RequestParam String email) {
        UserResponseDTO user = usersService.getUserByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Validated UserCreateDTO user) {
        UserResponseDTO userNew = usersService.save(user);
        return ResponseEntity.created(URI.create("/" + userNew.getId()))
        .body(userNew);
    }

    @PatchMapping("/updateProfile/{id}")
    public ResponseEntity<UserResponseDTO> updateProfile(@PathVariable Integer id, @RequestBody @Validated UserUpdateDTO user) {
        
        UserResponseDTO userUpdate = usersService.updateProfile(id, user);
        if (userUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        boolean isDeleted = usersService.deleteUserById(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
