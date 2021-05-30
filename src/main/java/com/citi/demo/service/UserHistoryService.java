package com.citi.demo.service;

import java.util.List;

import com.citi.demo.model.UserHistory;

//@Repository
public interface UserHistoryService{
	public UserHistory saveUserHistoryByuserId(String userId, String companySymbol, long quantity);
	public List<UserHistory> getUserHistoryByuserId(String userId);
	public List<String> getCompanySymbolsByUserId(String userId);
	public int deleteUserHistoryByuserId(int id);
	
	

}
