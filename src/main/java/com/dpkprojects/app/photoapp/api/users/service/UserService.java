package com.dpkprojects.app.photoapp.api.users.service;

import com.dpkprojects.app.photoapp.api.users.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	UserDto CreateUser(UserDto userDetails);

	UserDto getUserDetailsByEmail(String email);

	UserDto getUserByUserId(String userId);
}
