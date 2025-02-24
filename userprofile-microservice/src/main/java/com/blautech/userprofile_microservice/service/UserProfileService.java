package com.blautech.userprofile_microservice.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blautech.userprofile_microservice.dto.UserProfileCreateDTO;
import com.blautech.userprofile_microservice.dto.UserProfileResponseDTO;
import com.blautech.userprofile_microservice.dto.UserProfileUpdateDTO;
import com.blautech.userprofile_microservice.entity.UserProfile;
import com.blautech.userprofile_microservice.repository.UserProfileRepository;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ModelMapper mapper;
	

    public UserProfileResponseDTO getUserProfileByUserId(Integer userId) {

        Optional<List<UserProfile>> userProfiles = userProfileRepository.findByUserId(userId);

        if (userProfiles.isPresent() && !userProfiles.get().isEmpty()) {

            UserProfile userProfile = userProfiles.get().get(0);
            UserProfileResponseDTO userProfileResponseDTO = mapper.map(userProfile, UserProfileResponseDTO.class);
            return userProfileResponseDTO;

        }

        return null;
    }

    public UserProfileResponseDTO saveUserProfile(UserProfileCreateDTO userProfileDTO) {
        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setUserId(userProfileDTO.getUserId());
        newUserProfile.setFirstName(userProfileDTO.getFirstName());
        newUserProfile.setLastName(userProfileDTO.getLastName());
        newUserProfile.setShippingAddress(userProfileDTO.getShippingAddress());
        newUserProfile.setDateOfBirth(userProfileDTO.getDateOfBirth());
        UserProfile savedUserProfile = userProfileRepository.save(newUserProfile);
        return mapper.map(savedUserProfile, UserProfileResponseDTO.class);
    }

    public UserProfileResponseDTO updateUserProfile(Integer id, UserProfileUpdateDTO user) {
		Optional<List<UserProfile>> userProfileOptional = userProfileRepository.findByUserId(id);

		if (!userProfileOptional.isPresent()) {
			return null;
		}

		UserProfile userProfileEntity = mapper.map(user, UserProfile.class);
		userProfileEntity.setId(userProfileOptional.get().get(0).getId());

		UserProfile userProfileUpdate = userProfileRepository.save(userProfileEntity);
		return mapper.map(userProfileUpdate, UserProfileResponseDTO.class);
	}

	public boolean deleteUserProfileById(int id) {
		Optional<UserProfile> userProfileOptional = userProfileRepository.findById(id);
		if (!userProfileOptional.isPresent()) {
			return false;
		}
		userProfileRepository.deleteById(id);
		return true;
	}

}