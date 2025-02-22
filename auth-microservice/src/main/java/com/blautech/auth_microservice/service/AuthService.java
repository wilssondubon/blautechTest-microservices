package com.blautech.auth_microservice.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blautech.auth_microservice.client.UsersClient;
import com.blautech.auth_microservice.dto.UserCreateDTO;
import com.blautech.auth_microservice.dto.UserLoginCredentialsDTO;
import com.blautech.auth_microservice.dto.UserRegistryDTO;
import com.blautech.auth_microservice.dto.UserResponseDTO;
import com.blautech.auth_microservice.entity.UsersCredentials;
import com.blautech.auth_microservice.repository.UsersCredentialsRepository;

import feign.FeignException;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsersCredentialsRepository usersCredentialsRepository; 

    @Autowired
    private UsersClient usersClient;

    @Transactional
    public String registerUser(UserRegistryDTO userRegistry) {
        
        if (!userRegistry.getPassword().equals(userRegistry.getPasswordConfirmation())) {
            return "Passwords do not match";
        }

        try{
            UserResponseDTO userExist = usersClient.getUserByEmail(userRegistry.getEmail());

            if (userExist != null) {
                return "User already exists";
            }
        }
        catch (FeignException.NotFound e){
            System.out.println("Usuario no encontrado, continuando con el registro...");
        }
        

        UserCreateDTO userCreateDTO = new UserCreateDTO(
            userRegistry.getFirstName(), 
            userRegistry.getLastName(), 
            userRegistry.getEmail(), 
            userRegistry.getShippingAddress(), 
            userRegistry.getDateOfBirth());

        UserResponseDTO newUserResponse;

        try {
            newUserResponse = usersClient.saveUser(userCreateDTO);

            if (newUserResponse == null) {
                return "Error: User does not exist after creation";
            }

        } catch (Exception e) {
            return "Error saving user";
        }


        try {

            UsersCredentials usersCredentials = new UsersCredentials();
                
            usersCredentials.setUserId(newUserResponse.getId()); 
            usersCredentials.setPassword(passwordEncoder.encode(userRegistry.getPassword()));


            usersCredentialsRepository.save(usersCredentials);

        } catch (Exception e) {
            usersClient.deleteUserById(newUserResponse.getId());
            return "Error saving user credentials";
        }

        return "User registered successfully";
    }

    public String generateToken(UserLoginCredentialsDTO userLoginCredentials){

        UserResponseDTO userExist = usersClient.getUserByEmail(userLoginCredentials.getEmail());

        if (userExist == null) {
            return "User not exists";
        }

        Optional<UsersCredentials> credentials = usersCredentialsRepository.findByUserId(userExist.getId());

        if (!credentials.isPresent())
            return "Error getting credentials";

        if (!passwordEncoder.matches(userLoginCredentials.getPassword(), credentials.get().getPassword())) 
            return "Invalid credentials";
        
        return jwtService.generateToken(userExist.getId(), userLoginCredentials.getEmail());
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
