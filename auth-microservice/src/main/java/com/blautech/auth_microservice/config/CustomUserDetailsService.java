package com.blautech.auth_microservice.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blautech.auth_microservice.entity.User;
import com.blautech.auth_microservice.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> credential =  repository.findByEmail(email);
        return new CustomUserDetails(credential.get());
    }

}
