package com.citi.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citi.demo.model.UserMaster;

public interface UserRepository  extends JpaRepository<UserMaster, String>{
	
public UserMaster findUserMasterByUserId(String userId);
}





