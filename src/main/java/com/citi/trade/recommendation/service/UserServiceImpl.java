package com.citi.trade.recommendation.service;


import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.repository.UserRepository;


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

		UserMaster checkuser = null;
		String password = decodeString(userObject.getPassword());
		checkuser = userRepository.checkLogin(userObject,password);
		if(checkuser != null) {
			if(checkuser.getPassword().equals(decodeString(userObject.getPassword()).replaceAll("\\s",""))) {
				logger.info("User Login Successful. User: {} - ",userObject.getUserId());
			}
			else
			{
				logger.error("User Login UnsSuccessful");
			}
		}
		else {
			logger.error("User not found in database.User Login UnSuccessful" );
		}
		return checkuser;

	}
	public String decodeString(String encodedPassword) {

		String decoded = new String(Base64.getDecoder().decode(encodedPassword));
		StringBuilder password = new StringBuilder();
		password.append(decoded);
		password = password.reverse();
		return password.toString();
	}

}
