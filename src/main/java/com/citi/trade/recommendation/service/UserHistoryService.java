package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.model.UserHistory;

import java.util.List;

public interface UserHistoryService {
    public boolean saveUserHistoryByuserId(UserHistory history);

    public List<UserHistory> getUserHistoryByuserId(String userId);

    public List<String> getCompanySymbolsSavedByUserId(String userId);

    public int deleteUserHistoryByuserId(int[] ids);

    public int deleteUserHistoryByuserId(String userId);
}
