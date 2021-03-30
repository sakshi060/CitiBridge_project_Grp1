package com.pojo;

public class User {
	int user_id;
	String name;
	String username;
	public User(int user_id, String name, String username) {
	
		this.user_id = user_id;
		this.name = name;
		this.username = username;
	}
	public int getUserid() {
		return user_id;
	}
	public void setUserid(int userid) {
		this.user_id = userid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		return "\n User [User_Id = " + user_id + ", Name = " + name + ", Username = " + username + "]";
	}

}
