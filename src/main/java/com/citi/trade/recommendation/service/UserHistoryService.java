package com.citi.trade.recommendation.service;

import java.util.List;

import com.citi.trade.recommendation.model.UserHistory;

public interface UserHistoryService{
	public boolean saveUserHistoryByuserId(UserHistory history);
	public List<UserHistory> getUserHistoryByuserId(String userId);
	public List<String> getCompanySymbolsSavedByUserId(String userId);
	public int deleteUserHistoryByuserId(int[] ids);
	public int deleteUserHistoryByuserId(String userId);
}
