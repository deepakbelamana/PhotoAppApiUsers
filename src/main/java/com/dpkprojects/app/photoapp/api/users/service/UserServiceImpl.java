package com.dpkprojects.app.photoapp.api.users.service;

import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = userRepo.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity,UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity=userRepo.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
				true,true,true, true,new ArrayList<>());
	}
}
