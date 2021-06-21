package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.model.UserHistory;

import java.util.List;

 public interface UserHistoryService {
     boolean saveUserHistoryByuserId(UserHistory history);

     List<UserHistory> getUserHistoryByuserId(String userId);

     List<String> getCompanySymbolsSavedByUserId(String userId);

     int deleteUserHistoryByuserId(int[] ids);

     int deleteUserHistoryByuserId(String userId);
}
