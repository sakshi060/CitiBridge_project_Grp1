package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.UserMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class UserServiceTest {
    private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

    @Autowired
    UserService userService;


    @Test
     void testFindByUserName() {
        UserMaster user = new UserMaster();
        String userName = "Kiran";
        String password = "MzIxbmFyaUsNCg==";
        user.setUserId(userName);
        user.setPassword(password);


        logger.info("Authenticating User: {}", user.getUserId());
        UserMaster checkuser = userService.checkLogin(user);
        if (checkuser != null) {
            logger.info("SUCCESS");
        } else
            logger.error("FAILURE");

        Assertions.assertNotNull(checkuser);
        //Assertions.assertNull(userService.checkLogin(null));
    }

}