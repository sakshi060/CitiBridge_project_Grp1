package com.citi.tradeRecommendation.service;

import java.util.List;

import com.citi.tradeRecommendation.model.UserHistory;

public interface UserHistoryService{
	public UserHistory saveUserHistoryByuserId(String userId, String companySymbol, long quantity);
	public List<UserHistory> getUserHistoryByuserId(String userId);
	public List<String> getCompanySymbolsSavedByUserId(String userId);
	public int deleteUserHistoryByuserId(int id);
}
