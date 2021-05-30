package com.citi.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.citi.demo.model.UserMaster;
import com.citi.demo.repository.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)

public class UserServiceImplTest {
	
    @Autowired
    private UserRepository repo;
    
    private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
    
    @Test
    public void testFindByUser_name() {
		String user_name = "Kiran";
		String password = "Kiran123";
		UserMaster user = repo.findUserMasterByUserId(user_name);
		if(user != null) {
			logger.info("User found in database");
		}
		else {
			logger.info("User not found in database" );
		}
		
		if(user.getPassword().equals(password)) {
			logger.info("User Login Successful. User - "+user.getUserId());
		
		}
		else
		logger.info("User Login UnsSuccessful.Enter the correct Password!");
		
		
		assertThat(user).isNotNull();
    }	
    

}