package com.citi.trade.recommendation.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.citi.trade.recommendation.model.UserMaster;
import com.citi.trade.recommendation.repository.UserRepository;
import com.citi.trade.recommendation.util.PasswordUtil;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	public UserMaster checkLogin(UserMaster userObject) {
		// checks if user present in database and password matches

		UserMaster checkuser = null;
		if (userObject != null && !ObjectUtils.isEmpty(userObject.getUserId())
				&& !ObjectUtils.isEmpty(userObject.getPassword())) {
			String password = PasswordUtil.decodeString(userObject.getPassword());
			checkuser = userRepository.checkLogin(userObject, password);
			if (checkuser != null) {
				if (checkuser.getPassword()
						.equals(PasswordUtil.decodeString(userObject.getPassword()).replaceAll("\\s", ""))) {
					logger.info("User Login Successful. User: {} - ", userObject.getUserId());
				} else {
					logger.info("User Login UnSuccessful");
				}
			} else {
				logger.info("User not found in database.User Login UnSuccessful");
			}
		} else {
			logger.info("User ID/Password was empty");
		}
		return checkuser;

	}

}
