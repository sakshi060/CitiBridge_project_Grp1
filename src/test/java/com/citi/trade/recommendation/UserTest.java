package com.citi.trade.recommendation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.service.UserService;

@SpringBootTest
public class UserTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	UserService userService;


	@Test
	public void testFindByUserName() {
		UserMaster user = new UserMaster();
		UserMaster checkuser = new UserMaster();
		String userName = "Kiran";
		String password = "MzIxbmFyaUsNCg==";
		user.setUserId(userName);
		user.setPassword(password);


		logger.info("Authenticating User: {}",user.getUserId());
		checkuser = userService.checkLogin(user);
		if(checkuser!=null)
		{
			logger.info("SUCCESS");
		}
		else
			logger.error("FAILURE");
		
		Assertions.assertNotNull(checkuser);
		//Assertions.assertNull(userService.checkLogin(null));
	}

}