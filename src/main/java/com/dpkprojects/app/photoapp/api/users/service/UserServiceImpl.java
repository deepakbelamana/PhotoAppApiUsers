package com.dpkprojects.app.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpkprojects.app.photoapp.api.users.data.UserDataRepository;
import com.dpkprojects.app.photoapp.api.users.data.UserEntity;
import com.dpkprojects.app.photoapp.api.users.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDataRepository userRepo;

	@Override
	public UserDto CreateUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword("test");
		
		userRepo.save(userEntity);
		return null;
	}

}
