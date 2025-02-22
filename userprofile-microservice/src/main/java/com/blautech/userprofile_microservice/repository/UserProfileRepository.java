package com.blautech.userprofile_microservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blautech.userprofile_microservice.entity.UserProfile;


@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<List<UserProfile>> findByUserId(Integer userId);
}
