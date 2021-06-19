package com.citi.tradeRecommendation.service;


import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.UserMaster;
import com.citi.tradeRecommendation.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	JdbcTemplate template;
	@Autowired
	UserRepository userRepository;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Override
	public UserMaster checkLogin(UserMaster userObject) {
		// checks if user present in database and password matches

		UserMaster checkuser = new UserMaster();
		String password = decodeString(userObject.getPassword());
		checkuser = userRepository.checkLogin(userObject,password);
		if(checkuser != null) {
			System.out.println("\n");
			logger.info("User found in database");
		}
		else {
			System.out.println("\n");
			logger.info("User not found in database.User Login UnsSuccessful" );
			System.out.println("\n");
			return checkuser;
		}

		if(checkuser.getPassword().equals(decodeString(userObject.getPassword()).replaceAll("\\s",""))) {
			logger.info("User Login Successful. User: {} - ",userObject.getUserId());
			System.out.println("\n");
			return checkuser;

		}
		else
		{
			logger.info("User Login UnsSuccessful");
			System.out.println("\n");
			return null;

		}

	}
	public String decodeString(String encodedPassword) {

		String decoded = new String(Base64.getDecoder().decode(encodedPassword));
		StringBuilder password = new StringBuilder();
		password.append(decoded);
		password = password.reverse();
		return password.toString();
	}

}
