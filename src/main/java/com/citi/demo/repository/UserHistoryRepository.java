package com.citi.demo.repository;

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

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.UserHistory;
import com.citi.demo.service.SectorStocksService;
import com.citi.demo.service.StockRecommendationService;
@Repository
public class UserHistoryRepository {
	
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
	@Autowired
	JdbcTemplate template;

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	StockRecommendationService stockRecommendationService;
	
	UserHistory finalList[];

	UserHistory share = new UserHistory();

	public int addUserHistoryByuserId(String userId, String companySymbol, long quantity, String companySymbol_sector) {
		// TODO Auto-generated method stub
		// Saves User History
		try
		{
			
			stockRecommendationService.findStock(companySymbol);

			int added = template.update("insert into user_history(company_symbol,price,sector,user_id,volume) values(?,?,?,?,?)",
					companySymbol,stockRecommendationService.findStock(companySymbol).getPrice(),companySymbol_sector,userId,quantity);
			
			if(added ==1)
			{
				logger.info("Insertion Successful for User: "+userId);
				return added;
			}
		}
		catch(Exception e)
		{
			logger.info("Insertion could not be done!");
			
		}
		return 0;
	}

	
	public List<UserHistory> findUserHistoryByuserId(String userId) {
		// TODO Auto-generated method stub
		//Displays UserHistory
		
		ArrayList<UserHistory> finalStocks = new ArrayList<UserHistory>();
		try
		{

			String FINDSHARES = "select * from user_history where user_id=?";
			ArrayList<UserHistory> shares = (ArrayList<UserHistory>) template.query(FINDSHARES, new RowMapper<UserHistory>() {

				@Override
				public UserHistory mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new UserHistory(set.getInt(1), set.getString(2),set.getString(4),set.getBigDecimal(3),set.getString(5),set.getLong(6));
				}

			}, userId);
			finalList = new UserHistory[shares.size()];
			if(finalList.length != 0)
			{
			for(int i = 0;i<shares.size();i++)
			{
				finalList[i] = new UserHistory(shares.get(i).getId(), shares.get(i).getCompanySymbol(),shares.get(i).getSector(),shares.get(i).getPrice(),shares.get(i).getUserId(),shares.get(i).getVolume());
				finalStocks.add(finalList[i]);
			}

			logger.info("User History found for User: "+userId);
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
			logger.info("User History data could not be obtained!");
			return null;
		}

	}
	

	public List<String> findCompanySymbolsByUserId(String userId) {
		// TODO Auto-generated method stub
		try
		{

			String FINDSHARES = "select company_symbol from user_history where user_id=?";
			List<String> companySymbols = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new String(set.getString(1));
				}

			}, userId);
			logger.info("User History found for User: "+userId+" found!");
			return companySymbols;
		}
		catch(Exception e)
		{
			logger.info("User History data could not be obtained!");
			return null;
		}
	}

	
	public int deleteUserHistoryByuserId(int id) {
		// TODO Auto-generated method stub
		//Deletes selected stocks.
		try
		{
			String deleteQuery = "delete from user_history where id=?";
			int deleted = template.update(deleteQuery,id);
			return deleted;
		}
		catch(Exception e)
		{
			logger.info("User History data could not be deleted");
			return 0;
		}

	}

}
