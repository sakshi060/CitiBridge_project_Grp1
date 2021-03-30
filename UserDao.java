package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mapper.UserMapper;
import com.pojo.User;

@Repository
public class UserDao {
	
	public final static String query="select user_id,name,username from user;";
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<User> getUsers()
	{
		return jdbcTemplate.query(query, new UserMapper());
	}

}
