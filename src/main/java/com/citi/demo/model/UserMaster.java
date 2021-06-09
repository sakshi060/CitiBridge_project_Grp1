package com.citi.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "UserMaster")
@AllArgsConstructor
@NoArgsConstructor
public class UserMaster {
	
	@Id
	@Column(name ="userId")
	String userId;
	
	@Column(name ="password")
	String password;
	
	public UserMaster()
	{
		
	}

	public UserMaster(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}

	

	public UserMaster(String userId) {
		super();
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + "]";
	}
	


}
