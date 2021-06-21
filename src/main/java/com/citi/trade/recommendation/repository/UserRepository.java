package com.citi.trade.recommendation.repository;

import com.citi.trade.recommendation.model.UserMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private static final Logger logger = LogManager.getLogger(UserRepository.class);
    @Autowired
    JdbcTemplate template;

    public UserMaster checkLogin(UserMaster userObject, String password) {
        // checks if user present in database and password matches
        UserMaster temp;
        try {

            String findUser = "select * from user_master where user_id=?";
            temp = template.queryForObject(findUser, (set, arg1) -> {
                UserMaster userMaster = new UserMaster();
                userMaster.setUserId(set.getString(1));
                userMaster.setPassword(set.getString(2));
                return userMaster;
            }, userObject.getUserId());

            if (temp != null) {

                if (temp.getPassword().equals(password.replaceAll("\\s", ""))) {
                    return temp;
                } else {
                    logger.error("Enter the correct Password!");
                    return temp;

                }
            } else {
                logger.error("User not found in database");
                return null;
            }
        } catch (Exception e) {
            logger.error("Error Occured.User not found in database");
            return null;
        }

    }
}
