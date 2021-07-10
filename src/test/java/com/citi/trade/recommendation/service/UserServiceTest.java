package com.citi.trade.recommendation.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
	private static final Logger logger = LogManager.getLogger(UserServiceTest.class);

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	public UserMaster user = new UserMaster();

	@Test
	@Order(1)
	void testFindByUserName() {
		String userName = "XYZ";
		String password = "MzIxbmFyaUsNCg==";
		user.setUserId(userName);
		user.setPassword(password);
		Assertions.assertTrue(userRepository.addUser(user));

		logger.info("Authenticating User: {}", user.getUserId());
		UserMaster checkuser = userService.checkLogin(user);
		if (checkuser != null) {
			logger.info("SUCCESS");
		} else
			logger.error("FAILURE");

		Assertions.assertNotNull(checkuser);
	}

	@Test
	@Order(2)
	void deleteUser() {
		String userId = "XYZ";
		int deleted = userRepository.deleteUser(userId);
		Assertions.assertEquals(1, deleted);

	}

}