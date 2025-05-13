package com.dpkprojects.app.photoapp.api.users.ui.controllers;

import com.dpkprojects.app.photoapp.api.users.ui.model.UserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dpkprojects.app.photoapp.api.users.service.UserService;
import com.dpkprojects.app.photoapp.api.users.shared.UserDto;
import com.dpkprojects.app.photoapp.api.users.ui.model.CreateUserRequestModel;
import com.dpkprojects.app.photoapp.api.users.ui.model.CreateUserResponseModel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;

	@GetMapping("/status/check")
	public String checkStatus() {
		return "working on port " + env.getProperty("local.server.port")+",token : "+ env.getProperty("token.secret_key");
	}

	@PostMapping()
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		
		ModelMapper modelMapper = new ModelMapper();
		System.out.println(env.getProperty("gateway.ip"));
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		
		UserDto createduser = userService.CreateUser(userDto);
		
		CreateUserResponseModel createdUserResponseModel = modelMapper.map(createduser, CreateUserResponseModel.class);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUserResponseModel);
		
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseModel> getUserDetails(@PathVariable String userId) {
			UserDto userDto = userService.getUserByUserId(userId);
			UserResponseModel userResponseModel = new ModelMapper().map(userDto,UserResponseModel.class);
			return ResponseEntity.ok().body(userResponseModel);
	}

}
