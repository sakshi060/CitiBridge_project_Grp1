package com.citi.tradeRecommendation.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.UserHistory;
import com.citi.tradeRecommendation.service.SectorStocksService;
import com.citi.tradeRecommendation.service.StockDetailsService;
@Repository
public class UserHistoryRepository {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
	@Autowired
	JdbcTemplate template;

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	StockDetailsService stockDetailsService;

	UserHistory finalList[];

	UserHistory share = new UserHistory();

	public int addUserHistoryByuserId(String userId, String companySymbol, long quantity, String companySymbol_sector) {
		// Saves User History

		try
		{
			logger.info("Inserting into database User History for User "+userId);
			stockDetailsService.findStock(companySymbol);

			int added = template.update("insert into user_history(company_symbol,price,sector,user_id,volume) values(?,?,?,?,?)",
					companySymbol,stockDetailsService.findStock(companySymbol).getPrice(),companySymbol_sector,userId,quantity);

			if(added ==1)
			{
				logger.info("Insertion Successful for User: "+userId);
				return added;
			}
		}
		catch(Exception e)
		{
			logger.error("Insertion could not be done!");

		}
		return 0;
	}


	public List<UserHistory> findUserHistoryByuserId(String userId) {
		//Displays UserHistory

		ArrayList<UserHistory> finalStocks = new ArrayList<UserHistory>();
		try
		{
			logger.info("Fetching User History of user - " +userId);
			String FINDSHARES = "select * from user_history where user_id=?";
			ArrayList<UserHistory> shares = (ArrayList<UserHistory>) template.query(FINDSHARES, new RowMapper<UserHistory>() {

				@Override
				public UserHistory mapRow(ResultSet set, int arg1) throws SQLException {
					UserHistory userHistory = new UserHistory();
					userHistory.setId(set.getInt(1));
					userHistory.setCompanySymbol(set.getString(2));
					userHistory.setSector(set.getString(4));
					userHistory.setPrice(set.getBigDecimal(3));
					userHistory.setUserId(set.getString(5));
					userHistory.setVolume(set.getLong(6));
					return userHistory;
				}

			}, userId);
			finalList = new UserHistory[shares.size()];
			if(finalList.length != 0)
			{
				for(int i = 0;i<shares.size();i++)
				{
					UserHistory userHistory = new UserHistory();
					userHistory.setId(shares.get(i).getId());
					userHistory.setCompanySymbol(shares.get(i).getCompanySymbol());
					userHistory.setSector(shares.get(i).getSector());
					userHistory.setPrice(shares.get(i).getPrice());
					userHistory.setUserId(shares.get(i).getUserId());
					userHistory.setVolume(shares.get(i).getVolume());
					finalList[i] = userHistory;
					finalStocks.add(finalList[i]);
				}
				return finalStocks;
			}
			else
			{
				logger.info("User History data could not be obtained!");
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("User History data could not be obtained!");
			return null;
		}

	}


	public List<String> findCompanySymbolsByUserId(String userId) {

		try
		{
			logger.info("Fetching Company Symbols of saved stocks of user - " +userId);
			String FINDSHARES = "select company_symbol from user_history where user_id=?";
			List<String> companySymbols = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					return new String(set.getString(1));
				}

			}, userId);
			if(companySymbols.size()!=0)
				logger.info("User History found for User : "+userId);
			else
				logger.error("User History not found for User: "+userId);
			return companySymbols;
		}
		catch(Exception e)
		{
			logger.error("User History data could not be obtained!");
			return null;
		}
	}


	public int deleteUserHistoryByuserId(int id) {
		//Deletes selected stocks.

		try
		{
			logger.info("Deleting Stock with Stock ID {} ",id);
			String deleteQuery = "delete from user_history where id=?";
			int deleted = template.update(deleteQuery,id);
			return deleted;
		}
		catch(Exception e)
		{
			logger.error("User History data could not be deleted");
			return 0;
		}

	}

}
