package com.citi.trade.recommendation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.service.UserService;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class UserController {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	UserService userService;


	@PostMapping("/login")
	public boolean userLogin(@RequestBody UserMaster userObject ) {
		// Checks if user present in database, if yes returns userId

		UserMaster checkuser = new UserMaster();

			logger.info("Authenticating User: {}", userObject.getUserId());
			checkuser = userService.checkLogin(userObject);
		return checkuser != null;
	}
}
