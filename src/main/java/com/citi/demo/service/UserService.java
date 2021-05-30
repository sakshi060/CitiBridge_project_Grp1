package com.citi.demo.service;

import com.citi.demo.model.UserMaster;

public interface UserService {
	// checks if user present in database and password matches
		String checkLogin(UserMaster userObject);

}
