package com.citi.tradeRecommendation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.UserMaster;
import com.citi.tradeRecommendation.service.UserService;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	UserService userService;


	@PostMapping("/login")
	public boolean UserLogin(@RequestBody UserMaster userObject ) {
		// Checks if user present in database, if yes returns userId

		UserMaster checkuser = new UserMaster();
		try
		{
			logger.info("Authenticating User :"+userObject.getUserId());
			checkuser = userService.checkLogin(userObject);
			if(checkuser!=null)
			{
				logger.info("SUCCESS");
				return true;
			}
			else
				logger.error("FAILURE");
			return false;
		}
		catch(Exception e)
		{
			logger.error("FAILURE {}", e);
			return false;
		}

	}
}
