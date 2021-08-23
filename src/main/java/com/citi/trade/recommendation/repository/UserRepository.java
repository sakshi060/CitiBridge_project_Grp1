package com.citi.trade.recommendation.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.service.UserServiceImpl;

@Repository
public class UserRepository {

	private static final Logger logger = LogManager.getLogger(UserRepository.class);
	@Autowired
	JdbcTemplate template;

	public boolean addUser(UserMaster user) {
		// Saves Sector wise Stocks
		try {

			UserServiceImpl userServiceImpl = new UserServiceImpl();
			String password = userServiceImpl.decodeString(user.getPassword());

			logger.info("Inserting into database User {} ", user.getUserId());

			int added = template.update("insert into user_master(user_id,password) values(?,?)", user.getUserId(),
					password);

			if (added == 1) {
				logger.info("Insertion Successful for User {}", user.getUserId());
				return true;
			}
		} catch (Exception e) {
			logger.error("Insertion could not be done!");

		}
		return false;
	}

	public UserMaster checkLogin(UserMaster userObject, String password) {
		// checks if user present in database and password matches
		UserMaster temp = null;
		try {
			String findUser = "select * from user_master where user_id=?";
			temp = template.queryForObject(findUser, (set, arg1) -> new UserMaster(set.getString(1), set.getString(2)),
					userObject.getUserId());

			if (temp != null) {
				if (!temp.getPassword().equals(password.replaceAll("\\s", ""))) {
					logger.error("Enter the correct Password!");
					return null;
				}
			} else {
				logger.error("User not found in database");
			}
		} catch (Exception e) {
			logger.error("Error Occured.User not found in database");
		}
		return temp;
	}

	public int deleteUser(String userId) {
		// Deletes selected stocks.
		try {
			logger.info("Deleting User: {} ", userId);
			String deleteQuery = "delete from user_master where user_id=?";
			return template.update(deleteQuery, userId);
		} catch (Exception e) {
			logger.error("User: {} could not be deleted", userId);
			return 0;
		}
	}
}
