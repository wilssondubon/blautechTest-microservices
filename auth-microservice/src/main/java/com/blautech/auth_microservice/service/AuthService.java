package com.blautech.auth_microservice.service;


import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.blautech.auth_microservice.client.UserProfileClient;
import com.blautech.auth_microservice.dto.UserProfileCreateDTO;
import com.blautech.auth_microservice.dto.UserCreatedResponseDTO;
import com.blautech.auth_microservice.dto.UserLoginCredentialsDTO;
import com.blautech.auth_microservice.dto.UserRegistryDTO;
import com.blautech.auth_microservice.dto.UserProfileResponseDTO;
import com.blautech.auth_microservice.entity.User;
import com.blautech.auth_microservice.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private UserProfileClient userProfileClient;

    @Autowired
    private ModelMapper mapper;

    public UserCreatedResponseDTO registerUser(UserRegistryDTO userRegistry) {
        
        if (!userRegistry.getPassword().equals(userRegistry.getPasswordConfirmation())) {
            return new UserCreatedResponseDTO(false,  "Passwords do not match");
        }

        Optional<User> userExist = userRepository.findByEmail(userRegistry.getEmail());

        if (userExist.isPresent())
            return new UserCreatedResponseDTO(false, "User already exists");

        User usersNew = new User();
                
        usersNew.setEmail(userRegistry.getEmail()); 
        usersNew.setPassword(passwordEncoder.encode(userRegistry.getPassword()));
        
        User userCreated = userRepository.save(usersNew);


        UserProfileCreateDTO userProfileNew= new UserProfileCreateDTO(
            userCreated.getId(),
            userRegistry.getFirstName(), 
            userRegistry.getLastName(), 
            userRegistry.getShippingAddress(), 
            userRegistry.getDateOfBirth());


        UserProfileResponseDTO UserProfileCreated = new UserProfileResponseDTO();

        try {

            UserProfileCreated = userProfileClient.saveUser(userProfileNew);

        } catch (Exception e) {

            userRepository.deleteById(userCreated.getId());

            return new UserCreatedResponseDTO(false,"Error saving user profile");

        }

        UserCreatedResponseDTO response = mapper.map(UserProfileCreated, UserCreatedResponseDTO.class);
        response.setSuccess(true);
        response.setMessage("User register successfully");

        return response;
    }

    public String generateToken(UserLoginCredentialsDTO userLoginCredentials){

        Optional<User> userExist  = userRepository.findByEmail(userLoginCredentials.getEmail());

        if (!userExist.isPresent()) {
            return "User not exists";
        }

        if (!passwordEncoder.matches(userLoginCredentials.getPassword(), userExist.get().getPassword())) 
            return "Invalid credentials";
        
        return jwtService.generateToken(userExist.get().getId(), userLoginCredentials.getEmail());
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }

}
