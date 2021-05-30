package com.citi.demo.service;

import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.demo.BackendappApplication;

import com.citi.demo.model.UserMaster;
import com.citi.demo.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Override
	public String checkLogin(UserMaster userObject) {
		// TODO Auto-generated method stub

		String password = decodeString(userObject.getPassword());
		UserMaster temp = null;
		temp = userRepository.findUserMasterByUserId(userObject.getUserId());
		
		try
		{
		if(temp != null) {
			logger.info("User found in database");
		}
		else {
			logger.info("User not found in database" );
		}
		}
		catch(Exception e)
		{
			System.out.println("Invalid user!");
		}
		
		if(temp.getPassword().equals(password)) {
			try
			{
			logger.info("User Login Successful, User - "+temp.getUserId());
			return temp.getUserId();
			}
			catch(Exception e)
			{
				System.out.println("Invalid user!");
			}
			
		}
		else
		{
			try
			{
				logger.info("User Login UnsSuccessful.Enter the correct Password!");
			}
			catch(Exception e)
			{
				System.out.println("Invalid user!");
			}
		}
		return null;
		
		
	}
private String decodeString(String encodedPassword) {
		
		String decoded = new String(Base64.getDecoder().decode(encodedPassword));
		StringBuilder password = new StringBuilder();
		password.append(decoded);
		password = password.reverse();
		return password.toString();
	}

}
