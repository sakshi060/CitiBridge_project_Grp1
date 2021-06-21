package com.citi.trade.recommendation.controller;

import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public boolean userLogin(@RequestBody UserMaster userObject) {
        // Checks if user present in database, if yes returns userId
        logger.info("Authenticating User {} :", userObject.getUserId());
        return userService.checkLogin(userObject) != null;
    }
}
