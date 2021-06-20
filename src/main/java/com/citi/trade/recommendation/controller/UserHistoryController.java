package com.citi.trade.recommendation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.SectorStocksService;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;



@RequestMapping("/userHistory")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class UserHistoryController {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	StockDetailsService stockDetailsService;

	@PostMapping("/saveStocks/{userId}/{companySymbol}/{quantity}")
	public UserHistory saveUserHistory(@PathVariable String userId , @PathVariable String companySymbol, @PathVariable long quantity) {
		// Saves stock and quantity of the stock, the given user wants.

		UserHistory stock = new UserHistory();
		try
		{
			stock = userHistoryService.saveUserHistoryByuserId(userId,companySymbol,quantity);

			if(stock != null) {
				logger.info("User History for Stock {} and User: {} saved successfully!",stock.getCompanySymbol(),userId);
				return stock;
			}
			else
			{
				logger.error("User History for Stock {} and User: {} not saved successfully!",companySymbol,userId);
				return null;
			}
		}

		catch(Exception e)
		{
			logger.error("User History for Stock {} and User: {} not saved successfully!",stock.getCompanySymbol(),userId);
		}

		return new UserHistory();
	}

	@RequestMapping(value = "/showStocks/{userId}", method = RequestMethod.GET)
	public List<UserHistory> getUserHistory(@PathVariable String userId) {
		//Returns saved stocks of userId passed as an argument.

		List<UserHistory> userHistoryStocks = new ArrayList<>();
		try
		{
			userHistoryStocks = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
			if(!userHistoryStocks.isEmpty())
				logger.info("Showing User History for User: {}",userId);
		}
		catch(Exception e)
		{
			logger.error("User not found!");
		}
		return userHistoryStocks;
	}

	@RequestMapping(value = "/showTopPerformingStock/{userId}", method = RequestMethod.GET)
	public StockDetails getTopPerformingStock(@PathVariable String userId) throws IOException {
		//Returns Top Performing Stock from Saved Stocks of userId passed as an argument.

		StockDetails topStock = new StockDetails();
		List<String> companySymbols=new ArrayList<>();
		try 
		{
			logger.info("Finding Top Performance Stock for User: {} " ,userId);
			companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
			topStock = stockDetailsService.findTopPerformingStock(companySymbols);
			if(topStock!=null)
				logger.info("Showing Top Performing Stock for User: {}",userId);
		}
		catch(Exception e)
		{
			logger.error("Top Performing Stock for User: {} could not be found!",userId);
		}
		return topStock;
	}

	@RequestMapping(value = "/getCompanySymbols/{userId}", method = RequestMethod.GET)
	public List<String> getCompanySymbolsSavedByUserId(@PathVariable String userId) {
		//Returns Company Symbols of Saved Stocks of userId passed as an argument.

		List<String> companySymbols=new ArrayList<>();
		try
		{
			companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
			if(companySymbols!= null && companySymbols.size()!=0)
				logger.info("Company Symbols of Stocks saved by User: {} found!",userId);

		}
		catch(Exception e)
		{
			logger.error("Company Symbols of Stocks saved by User: {} could not be found!",userId);
		}
		return companySymbols;

	}

	@RequestMapping(value = "/deleteSavedStocksByUserId", method = RequestMethod.POST)
	public boolean deleteSavedStocksByUserId( @RequestBody int[] ids) {
		// Deletes stocks for the logged in user with ids as parameter.

		try {
			int deleted = userHistoryService.deleteUserHistoryByuserId(ids);
			if(deleted==1) {
				logger.info("Deleted Stock!");
			}
			else
				logger.error("Stock could not be deleted!");

			return true;
		}
		catch(Exception e)
		{
			logger.error("Invalid ID!");
			return false;
		}
	}



}
