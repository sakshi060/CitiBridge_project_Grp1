package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.UserDao;
import com.pojo.User;

@Service
public class UserService {
	@Autowired
	UserDao userdao;
	
	public List<User> getUsers()
	{
		return userdao.getUsers();
		
	}
	

}
