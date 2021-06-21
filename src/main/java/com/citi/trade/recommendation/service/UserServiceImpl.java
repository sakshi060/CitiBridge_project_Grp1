package com.citi.trade.recommendation.service;


import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserMaster checkLogin(UserMaster userObject) {
        // checks if user present in database and password matches

        UserMaster checkuser;
        String password = decodeString(userObject.getPassword());
        checkuser = userRepository.checkLogin(userObject, password);
        if (checkuser != null) {
            if (checkuser.getPassword().equals(decodeString(userObject.getPassword()).replaceAll("\\s", ""))) {
                logger.info("User Login Successful. User: {} - ", userObject.getUserId());
            } else {
                logger.info("User Login UnsSuccessful");
            }
        } else {
            logger.info("User not found in database.User Login UnSuccessful");
        }
        return checkuser;

    }

    public String decodeString(String encodedPassword) {

        String decoded = new String(Base64.getDecoder().decode(encodedPassword));
        StringBuilder password = new StringBuilder();
        password.append(decoded);
        password.reverse();
        return password.toString();
    }

}
