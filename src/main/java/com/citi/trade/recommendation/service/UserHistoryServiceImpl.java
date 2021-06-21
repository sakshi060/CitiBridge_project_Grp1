package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.repository.UserHistoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class UserHistoryServiceImpl implements UserHistoryService {

    private static final Logger logger = LogManager.getLogger(UserHistoryServiceImpl.class);

    @Autowired
    UserHistoryRepository userHistoryRepository;

    @Override
    public boolean saveUserHistoryByuserId(UserHistory history) {
        // Saves User History

        int added = 0;
        try {
            if (history != null) {
                added = userHistoryRepository.addUserHistoryByuserId(history);
                if (added == 1) {

                    logger.info("Stock of {} added!", history.getCompanySymbol());
                }
            }

        } catch (Exception e) {
            logger.error("Stock of {} could not be added!", e.getMessage());
        }
        return added == 1;
    }

    @Override
    public List<UserHistory> getUserHistoryByuserId(String userId) {
        //Displays UserHistory

        ArrayList<UserHistory> finalStocks = new ArrayList<>();
        try {
            finalStocks = (ArrayList<UserHistory>) userHistoryRepository.findUserHistoryByuserId(userId);
            if (!finalStocks.isEmpty())
                logger.info("User History of User: {} Found!", userId);
            else
                logger.error("User History of User: {} not Found!", userId);

        } catch (Exception e) {
            logger.error("User History not Found!");
        }
        return finalStocks;

    }

    @Override
    public List<String> getCompanySymbolsSavedByUserId(String userId) {

        List<String> companySymbols;
        List<String> distinctcompanySymbols = new ArrayList<>();
        try {
            companySymbols = userHistoryRepository.findCompanySymbolsByUserId(userId);
            HashMap<String, Integer> hm = new HashMap<>();
            for (int i = 0; i < companySymbols.size(); i++) {
                hm.put(companySymbols.get(i), i);
            }
            distinctcompanySymbols.addAll(hm.keySet());
            if (!companySymbols.isEmpty())
                logger.info("Company Symbols of Stocks saved by User: {} Found!", userId);
        } catch (Exception e) {
            logger.error("Company Symbols of Stocks saved by User: {} not Found!", userId);
        }
        return distinctcompanySymbols;
    }

    @Override
    public int deleteUserHistoryByuserId(int[] ids) {
        //Deletes selected stocks.
        int deleted = 0;
        try {
            for (int id : ids) {
                deleted = userHistoryRepository.deleteUserHistoryByuserId(id);
                logger.info("User History data of ID: {} deleted", id);
            }
        } catch (Exception e) {
            logger.error("User History could not be deleted");
        }
        return deleted;
    }

    @Override
    public int deleteUserHistoryByuserId(String userId) {
        //Deletes all stocks of a particular user.

        int deleted = 0;
        try {
            deleted = userHistoryRepository.deleteUserHistoryByuserId(userId);
            logger.info("User History of User {} deleted", userId);
        } catch (Exception e) {
            logger.error("User History data could not be deleted");
        }
        return deleted;
    }

}



