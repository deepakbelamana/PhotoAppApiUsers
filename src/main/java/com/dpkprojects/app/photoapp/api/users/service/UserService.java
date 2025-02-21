package com.dpkprojects.app.photoapp.api.users.service;

import com.dpkprojects.app.photoapp.api.users.shared.UserDto;

public interface UserService {
	
	UserDto CreateUser(UserDto userDetails);
}
