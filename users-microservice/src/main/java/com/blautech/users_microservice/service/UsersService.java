package com.blautech.users_microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.blautech.users_microservice.repository.UsersRepository;
import com.blautech.users_microservice.dto.UserCreateDTO;
import com.blautech.users_microservice.dto.UserResponseDTO;
import com.blautech.users_microservice.dto.UserUpdateDTO;
import com.blautech.users_microservice.entity.Users;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

import java.util.List;

import org.modelmapper.TypeToken;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
    private ModelMapper mapper;

	@Autowired
	private RestTemplate restTemplate;
	
	public List<UserResponseDTO> getAll(){
		List<Users> users = usersRepository.findAll();
		return mapper.map(users, new TypeToken<List<UserResponseDTO>>() {}.getType());
	}
	
	public UserResponseDTO getUserById(int id) {
		Optional<Users> user = usersRepository.findById(id);
		if (!user.isPresent())
			return null;

		return user.map(u -> mapper.map(u, UserResponseDTO.class)).orElse(null);
	}
	
	public UserResponseDTO save(UserCreateDTO user) {
		Users userNew = usersRepository.save(mapper.map(user, Users.class));
		return mapper.map(userNew, UserResponseDTO.class);
	}

	public UserResponseDTO updateProfile(Integer id, UserUpdateDTO user) {
		Optional<Users> userOptional = usersRepository.findById(id);

		if (!userOptional.isPresent()) {
			return null;
		}

		Users userEntity = mapper.map(user, Users.class);
		userEntity.setId(id);
		userEntity.setPassword(userOptional.get().getPassword());

		Users userUpdate = usersRepository.save(userEntity);
		return mapper.map(userUpdate, UserResponseDTO.class);
	}
}
