package com.citi.demo.service;

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
import org.springframework.stereotype.Service;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.UserHistory;
import com.citi.demo.service.StockWrapperServiceImpl;


@Repository(value="dao")
@Service
public class UserHistoryServiceImpl implements UserHistoryService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	JdbcTemplate template;
	UserHistory share = new UserHistory();
	@Autowired
	SectorStocksService sector;
	UserHistory finalList[];

	@Override
	public UserHistory saveUserHistoryByuserId(String userId, String companySymbol, long quantity) {
		// TODO Auto-generated method stub
		UserHistory share = new UserHistory();
		try
		{
			String sectors; 
			sectors = sector.findSectorByCompanySymbol(companySymbol);
			
			StockWrapperServiceImpl.findStock(companySymbol);
			
			int added = template.update("insert into user_history(company_symbol,price,sector,user_id,volume) values(?,?,?,?,?)",
					companySymbol,StockWrapperServiceImpl.findStock(companySymbol).getPrice(),sector,userId,quantity);
			logger.info("Insertion Successful for User: "+userId);
			 if(added ==1)
				 {
					share.setUserId(userId);
					share.setPrice(StockWrapperServiceImpl.findStock(companySymbol).getPrice());
					share.setCompanySymbol(companySymbol);
					share.setSector(sectors);
					share.setVolume(StockWrapperServiceImpl.findStock(companySymbol).getVolume());
					return share;
				 }
		}
		catch(Exception e)
		{
			logger.info("Insertion could not be done!");
			return null;
		}
		return share;
	}

	@Override
	public List<UserHistory> getUserHistoryByuserId(String userId) {
		// TODO Auto-generated method stub
		ArrayList<UserHistory> shares = new ArrayList<UserHistory>();
		ArrayList<UserHistory> finalStocks = new ArrayList<UserHistory>();
		try
		{
			
			String FINDSHARES = "select * from user_history where user_id=?";
			shares = (ArrayList<UserHistory>) template.query(FINDSHARES, new RowMapper<UserHistory>() {
				
				@Override
				public UserHistory mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new UserHistory(set.getInt(1), set.getString(2),set.getString(4),set.getBigDecimal(3),set.getString(5),set.getLong(6));
				}

			}, userId);
			System.out.println(shares);
			finalList = new UserHistory[shares.size()];
			for(int i = 0;i<shares.size();i++)
			{
				finalList[i] = new UserHistory(shares.get(i).getId(), shares.get(i).getCompanySymbol(),shares.get(i).getSector(),shares.get(i).getPrice(),shares.get(i).getUserId(),shares.get(i).getVolume());
				finalStocks.add(finalList[i]);
			}
			
			logger.info("User History found for User: "+userId+" found!");
			return finalStocks;
		}
		catch(Exception e)
		{
			logger.info("User History data could not be obtained!");
			return null;
		}



	}



	@Override
	public List<String> getCompanySymbolsByUserId(String userId) {
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

	@Override
	public int deleteUserHistoryByuserId(int id) {
		// TODO Auto-generated method stub
		
			String deleteQuery = "delete from user_history where id=?";
			int deleted = template.update(deleteQuery,id);
		
		return deleted;
	}



	

}



