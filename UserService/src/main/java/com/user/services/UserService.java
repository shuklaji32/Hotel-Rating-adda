package com.user.services;

import java.util.List;

import com.user.entities.User;

public interface UserService {
	
	//create
	User saveUser(User user);
	
	//get all user
	List<User> getAllUser();
	
	//get a single user
	User getUser(String userId);
	
	//delete a user
	void  deleteUser(User user);
	
	//update a user
	void updateUser(User user);
}
