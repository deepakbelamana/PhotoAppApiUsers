package com.dpkprojects.app.photoapp.api.users.shared;

import com.dpkprojects.app.photoapp.api.users.ui.model.album.AlbumResponseModel;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7381530608719953416L;
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String userId;
	private String encryptedPassword;
	List<AlbumResponseModel> albumResponseModelList;

	public List<AlbumResponseModel> getAlbumResponseModelList() {
		return albumResponseModelList;
	}

	public void setAlbumResponseModelList(List<AlbumResponseModel> albumResponseModelList) {
		this.albumResponseModelList = albumResponseModelList;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

}
