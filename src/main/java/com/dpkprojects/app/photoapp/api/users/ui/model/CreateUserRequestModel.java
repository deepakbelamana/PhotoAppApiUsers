package com.dpkprojects.app.photoapp.api.users.ui.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequestModel {
	
	@NotNull(message = "first name cannot be null")
	@Size(min = 2, message = "first name should have atleast 2 characters")
	private String firstName;
	
	@NotNull(message = "last name cannot be null")
	@Size(min = 2, message = "last name should have atleast 2 characters")
	private String lastName;
	
	@NotNull(message = "password cannot be null")
	@Size(min = 8,max = 16, message = "password should have atleast minimum 8 characters and maximum 16 characters")
	private String password;
	
	@NotNull(message = "email cannot be null")
	@Email
	private String email;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
