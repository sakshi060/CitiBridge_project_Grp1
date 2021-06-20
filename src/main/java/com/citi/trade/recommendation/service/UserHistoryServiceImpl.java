package com.citi.trade.recommendation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.repository.UserHistoryRepository;


@Service
public class UserHistoryServiceImpl implements UserHistoryService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	UserHistoryRepository userHistoryRepository;
	@Autowired
	StockDetailsService stockDetailsService;
	UserHistory[] finalList;

	UserHistory stock = new UserHistory();

	@Override
	public UserHistory saveUserHistoryByuserId(String userId, String companySymbol, long quantity) {
		// Saves User History

		int added = 0;
		String companySymbolSector = null; 
		try
		{
			companySymbolSector = sectorStocksService.getSectorByCompanySymbol(companySymbol);
			if (companySymbolSector!= null)
			{
				added = userHistoryRepository.addUserHistoryByuserId(userId, companySymbol, quantity,companySymbolSector);
				if(added ==1)
				{
					stock.setUserId(userId);
					stock.setPrice(stockDetailsService.findStock(companySymbol).getPrice());
					stock.setCompanySymbol(companySymbol);
					stock.setSector(companySymbolSector);
					stock.setVolume(quantity);
					logger.info("Stock of {} added!",companySymbol);
				}
			}
			else
			{
				logger.error("Company Symbol - {} not found!",companySymbol);
				return null;
			}
		}
		catch(Exception e)
		{
			logger.error("Stock of {} could not be added!",companySymbol);	
		}
		return stock;
	}

	@Override
	public List<UserHistory> getUserHistoryByuserId(String userId) {
		//Displays UserHistory

		ArrayList<UserHistory> finalStocks = new ArrayList<>();
		try
		{
			finalStocks = (ArrayList<UserHistory>) userHistoryRepository.findUserHistoryByuserId(userId);
			if(finalStocks!=null && finalStocks.size()!=0)
				logger.info("User History of User: {} Found!",userId);
			else
				logger.error("User History of User: {} not Found!",userId);

		}
		catch(Exception e)
		{
			logger.error("User History not Found!");
		}
		return finalStocks;

	}

	@Override
	public List<String> getCompanySymbolsSavedByUserId(String userId) {

		List<String> companySymbols = new ArrayList<>();
		List<String> distinctcompanySymbols = new ArrayList<>();
		try
		{
			companySymbols = userHistoryRepository.findCompanySymbolsByUserId(userId);
			HashMap<String,Integer> hm = new HashMap<>();
			for (int i = 0; i < companySymbols.size(); i++) {
				hm.put(companySymbols.get(i), i);
			}
		    distinctcompanySymbols.addAll(hm.keySet());
			if(!companySymbols.isEmpty())
				logger.info("Company Symbols of Stocks saved by User: {} Found!",userId);
		}
		catch(Exception e)
		{
			logger.error("Company Symbols of Stocks saved by User: {} not Found!",userId);
		}
		return distinctcompanySymbols;
	}

	@Override
	public int deleteUserHistoryByuserId(int[] ids) {
		//Deletes selected stocks.

		int deleted = 0;
		try
		{
			for(int i = 0;i<ids.length;i++) {
				deleted = userHistoryRepository.deleteUserHistoryByuserId(ids[i]);
				logger.info("User History data of ID: {} deleted",ids[i]);
			}
		}
		catch(Exception e)
		{
			logger.error("User History could not be deleted");
		}
		return deleted;
	}

	@Override
	public int deleteUserHistoryByuserId(String userId) {
		//Deletes selected stocks.

		int deleted = 0;
		try
		{
			deleted = userHistoryRepository.deleteUserHistoryByuserId(userId);
			logger.info("User History of User {} deleted",userId);
		}
		catch(Exception e)
		{
			logger.error("User History data could not be deleted");
		}
		return deleted;
	}

}



