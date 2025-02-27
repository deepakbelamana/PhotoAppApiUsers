package com.dpkprojects.app.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dpkprojects.app.photoapp.api.users.data.UserDataRepository;
import com.dpkprojects.app.photoapp.api.users.data.UserEntity;
import com.dpkprojects.app.photoapp.api.users.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDataRepository userRepo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto CreateUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		userRepo.save(userEntity);
		
		UserDto userDto = modelMapper.map(userEntity, UserDto.class);
		return userDto;
	}

}
