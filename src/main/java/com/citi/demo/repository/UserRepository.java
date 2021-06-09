package com.citi.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.UserMaster;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate template;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	public UserMaster checkLogin(UserMaster userObject, String password) {
		// TODO Auto-generated method stub
		// checks if user present in database and password matches
		//String password = decodeString(userObject.getPassword());
		UserMaster temp = null;
		try
		{

			String FINDUSER = "select * from user_master where user_id=?";
			temp = (UserMaster) template.queryForObject(FINDUSER, new RowMapper<UserMaster>() {

				@Override
				public UserMaster mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new UserMaster(set.getString(1),set.getString(2));
				}

			}, userObject.getUserId());

			if(temp != null) {
				
				if(temp.getPassword().equals(password.replaceAll("\\s",""))) {
					logger.info("User Login Successful for user: "+temp.getUserId());
					return temp;
				}
				else
				{
					logger.info("User Login UnsSuccessful.Enter the correct Password!");
					return temp;

				}
			}
			else
			{
				logger.info("User not found in database");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.info("Error Occured.User not found in database");
			return null;
		}

	}
}
