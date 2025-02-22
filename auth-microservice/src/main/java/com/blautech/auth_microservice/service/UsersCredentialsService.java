package com.blautech.auth_microservice.service;

import com.blautech.auth_microservice.entity.UsersCredentials;
import com.blautech.auth_microservice.repository.UsersCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersCredentialsService {

    @Autowired
    private UsersCredentialsRepository usersCredentialsRepository;

    public UsersCredentials save(UsersCredentials usersCredentials) {
        return usersCredentialsRepository.save(usersCredentials);
    }

    public Optional<UsersCredentials> findById(Integer id) {
        return usersCredentialsRepository.findById(id);
    }

    public void deleteById(Integer id) {
        usersCredentialsRepository.deleteById(id);
    }

    public Optional<UsersCredentials> findByUserId(Integer userId) {
        return usersCredentialsRepository.findByUserId(userId);
    }
}