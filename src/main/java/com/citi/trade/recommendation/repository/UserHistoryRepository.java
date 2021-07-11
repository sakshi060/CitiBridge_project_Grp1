package com.citi.trade.recommendation.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.citi.trade.recommendation.model.UserHistory;

@Repository
public class UserHistoryRepository {

	private static final Logger logger = LogManager.getLogger(UserHistoryRepository.class);
	@Autowired
	JdbcTemplate template;

	public int addUserHistoryByuserId(UserHistory history) {
		// Saves User History
		try {
			logger.info("Inserting into database User History for User: {} ", history.getUserId());

			int added = template.update(
					"insert into user_history(company_symbol,price,sector,user_id,volume) values(?,?,?,?,?)",
					history.getCompanySymbol(), history.getPrice(), history.getSector(), history.getUserId(),
					history.getVolume());

			if (added == 1) {
				logger.info("Insertion Successful for User: {} ", history.getUserId());
				return added;
			}
		} catch (Exception e) {
			logger.error("Insertion could not be done!");

		}
		return 0;
	}

	public List<UserHistory> findUserHistoryByuserId(String userId) {
		// Displays UserHistory
		List<UserHistory> userHistoryList = new ArrayList<>();
		try {
			logger.info("Fetching User History of User: {} ", userId);
			String query = "select * from user_history where user_id=?";
			userHistoryList = template.query(query, (set, arg1) -> new UserHistory(set.getInt(1), set.getString(2),
					set.getString(4), set.getBigDecimal(3), set.getString(5), set.getLong(6)), userId);
		} catch (Exception e) {
			logger.error("User History of User: {}  could not be obtained!", userId);
		}
		return userHistoryList;

	}

	public List<String> findCompanySymbolsByUserId(String userId) {
		List<String> companySymbols = new ArrayList<>();
		try {
			logger.info("Fetching Company Symbols of saved stocks of user - {}", userId);
			String query = "select company_symbol from user_history where user_id=?";
			companySymbols = template.query(query, (set, arg1) -> set.getString(1), userId);
			if (!companySymbols.isEmpty())
				logger.info("User History found for User: {} ", userId);
			else
				logger.error("User History not found for User: {}", userId);
		} catch (Exception e) {
			logger.error("User History of User: {} could not be obtained!", userId);
		}
		return companySymbols;
	}

	public int deleteUserHistoryByuserId(int id) {
		// Deletes selected stocks.
		try {
			logger.info("Deleting Stock with Stock ID - {} ", id);
			String deleteQuery = "delete from user_history where id=?";
			return template.update(deleteQuery, id);
		} catch (Exception e) {
			logger.error("User History with Stock ID - {} id could not be deleted", id);
			return 0;
		}

	}

	public int deleteUserHistoryByuserId(String userId) {
		// Deletes selected stocks.
		try {
			logger.info("Deleting Stock for User {} ", userId);
			String deleteQuery = "delete from user_history where user_id=?";
			return template.update(deleteQuery, userId);
		} catch (Exception e) {
			logger.error("User History of User: {} could not be deleted", userId);
			return 0;
		}

	}

}
