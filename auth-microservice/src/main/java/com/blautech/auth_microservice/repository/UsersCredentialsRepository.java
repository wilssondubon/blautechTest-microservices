package com.blautech.auth_microservice.repository;

import com.blautech.auth_microservice.entity.UsersCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersCredentialsRepository extends JpaRepository<UsersCredentials, Integer> {
    Optional<UsersCredentials> findByUserId(Integer userId);
}
