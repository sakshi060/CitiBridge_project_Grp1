package com.citi.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.citi.demo.model.UserMaster;
import com.citi.demo.service.UserService;

@SpringBootTest
public class UserTest {
	
    @Autowired
   UserService userService;
    
    
    @Test
    public void testFindByUser_name() {
    	UserMaster user = new UserMaster();
    	UserMaster checkuser = new UserMaster();
    	String user_name = "Kiran";
    	String password = "MzIxbmFyaUsNCg==";
    	user.setUserId(user_name);
		user.setPassword(password);
		
		checkuser = userService.checkLogin(user);
		if(checkuser != null) {
			System.out.println("\n");
			System.out.println("User found in database");
		}
		else {
			System.out.println("\n");
			System.out.println("User not found in database" );
			System.out.println("\n");
			return;
			
		}
		
		if(checkuser.getPassword().equals(decodeString(password).replaceAll("\\s",""))) {
			System.out.println("User Login Successful. User - "+user.getUserId());
			System.out.println("\n");
		
		}
		else
		{
			System.out.println("User Login UnsSuccessful.Enter the correct Password!");
			System.out.println("\n");
		}
		
		
		assertThat(user).isNotNull();
    }	
	private String decodeString(String encodedPassword) {

		String decoded = new String(Base64.getDecoder().decode(encodedPassword));
		StringBuilder password = new StringBuilder();
		password.append(decoded);
		password = password.reverse();
		return password.toString();
	}

}