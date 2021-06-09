package com.citi.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.UserHistory;
import com.citi.demo.repository.UserHistoryRepository;


@Service
public class UserHistoryServiceImpl implements UserHistoryService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	UserHistoryRepository userHistoryRepository;
	@Autowired
	StockRecommendationService stockRecommendationService;
	UserHistory finalList[];

	UserHistory stock = new UserHistory();

	@Override
	public UserHistory saveUserHistoryByuserId(String userId, String companySymbol, long quantity) {
		// TODO Auto-generated method stub
		// Saves User History
		int added = 0;
		String companySymbol_sector; 
		companySymbol_sector = sectorStocksService.getSectorByCompanySymbol(companySymbol);
		try
		{
			added = userHistoryRepository.addUserHistoryByuserId(userId, companySymbol, quantity,companySymbol_sector);
			if(added ==1)
			{
				stock.setUserId(userId);
				stock.setPrice(stockRecommendationService.findStock(companySymbol).getPrice());
				stock.setCompanySymbol(companySymbol);
				stock.setSector(companySymbol_sector);
				stock.setVolume(quantity);
				logger.info("Stock of "+companySymbol+" added!");
			}
		}
		catch(Exception e)
		{
			logger.info("Stock of "+companySymbol+" not added!");	
		}
		return stock;
	
	}

	@Override
	public List<UserHistory> getUserHistoryByuserId(String userId) {
		// TODO Auto-generated method stub
		//Displays UserHistory
		
		ArrayList<UserHistory> finalStocks = new ArrayList<UserHistory>();
		try
		{
		finalStocks = (ArrayList<UserHistory>) userHistoryRepository.findUserHistoryByuserId(userId);
		if(finalStocks.size()!=0)
		logger.info("User History of user: "+userId+" Found!");
		else
			logger.info("User History not Found!");
		//return finalStocks;
		}
		catch(Exception e)
		{
			logger.info("User History not Found!");
		}
		return finalStocks;

	}
	
	@Override
	public List<String> getCompanySymbolsSavedByUserId(String userId) {
		// TODO Auto-generated method stub
		List<String> companySymbols = new ArrayList<>();
		try
		{
		companySymbols = userHistoryRepository.findCompanySymbolsByUserId(userId);
		logger.info("Company Symbols of Stocks saved by user: "+userId+" Found!");
		//return companySymbols;
		}
		catch(Exception e)
		{
			logger.info("Company Symbols of Stocks saved by user: "+userId+" not Found!");
		}
		return companySymbols;
	}

	@Override
	public int deleteUserHistoryByuserId(int id) {
		// TODO Auto-generated method stub
		//Deletes selected stocks.
		int deleted = 0;
		try
		{
			deleted = userHistoryRepository.deleteUserHistoryByuserId(id);
			logger.info("User History data of ID: "+id+" deleted");
			//return deleted;
		}
		catch(Exception e)
		{
			logger.info("User History data could not be deleted");
			
		}
		return deleted;
	}

}



