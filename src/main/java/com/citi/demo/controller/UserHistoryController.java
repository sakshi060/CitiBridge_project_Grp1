package com.citi.demo.controller;

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

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.StockDetails;
import com.citi.demo.model.UserHistory;
import com.citi.demo.service.SectorStocksService;
import com.citi.demo.service.StockRecommendationService;
import com.citi.demo.service.UserHistoryService;

import yahoofinance.histquotes.HistoricalQuote;


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
	StockRecommendationService stockRecomendationService;

	@PostMapping("/saveStocks/{userId}/{companySymbol}/{quantity}")
	public UserHistory saveUserHistory(@PathVariable String userId , @PathVariable String companySymbol, @PathVariable long quantity) {
		// Saves stock and quantity of the stock, the given user wants.
		UserHistory stock = new UserHistory();
		try
		{
			stock = userHistoryService.saveUserHistoryByuserId(userId,companySymbol,quantity);
			if(stock.getCompanySymbol()!=null) {
				logger.info("User History for Stock {} and User {} saved successfully!",stock.getCompanySymbol(),userId);
				return stock;
			}
		}
		catch(Exception e)
		{
			logger.error("User History for Stock {} and User {} not saved successfully!",stock.getCompanySymbol(),userId);
		}

		return new UserHistory();
	}

	@RequestMapping(value = "/showStocks/{userId}", method = RequestMethod.GET)
	public ArrayList<UserHistory> getUserHistory(@PathVariable String userId) {
		//Returns saved stocks of userId passed as an argument.
		ArrayList<UserHistory> stocks = new ArrayList<UserHistory>();

		try
		{
			logger.info("User History of user - " +userId);
			stocks = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
		}
		catch(Exception e)
		{
			logger.error("User not found!");
		}
		return stocks;
	}

	@RequestMapping(value = "/showTopPerformingStock/{userId}", method = RequestMethod.GET)
	public StockDetails getTopPerformingStock(@PathVariable String userId) throws IOException {
		//Returns Top Performing Stock from Saved Stocks of userId passed as an argument.
		StockDetails topStock = new StockDetails();

		List<String> companySymbols=new ArrayList<String>(); 
		logger.info("Top Performing Stock for User: "+userId);
		try 
		{
			companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
			topStock = stockRecomendationService.getStocksDetails(companySymbols.get(0));			
		}
		catch(Exception e)
		{
			logger.error("Top Performing Stock for User: "+userId+" could not be found!");
		}
		return topStock;
	}
	
	@RequestMapping(value = "/getCompanySymbols/{userId}", method = RequestMethod.GET)
	public List<String> getCompanySymbolsSavedByUserId(String userId) {
		//Returns Company Symbols of Saved Stocks of userId passed as an argument.
		List<String> companySymbols=new ArrayList<String>();  
		logger.info("Searching Company Symbols of Stocks saved by User: "+userId);
		try
		{
			companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
			logger.info("Company Symbols of Stocks saved by User: "+userId+ " found!");
			
		}
		catch(Exception e)
		{
			logger.error("Company Symbols of Stocks saved by User: "+userId+ " could not be found!");
		}
		return companySymbols;

	}

	@RequestMapping(value = "/deleteSavedStocksByUserId", method = RequestMethod.POST)
	public boolean deleteSavedStocksByUserId( @RequestBody int[] ids) {
		// Deletes stocks for the logged in user with ids as parameter.
		try {
			System.out.println(ids[0]);
			for(int i=0;i<ids.length;i++)
			{
				int deleted = userHistoryService.deleteUserHistoryByuserId(ids[i]);
				if(deleted==1) {
					logger.info("Deleted Stock!");
				}
				else
					logger.info("Stock could not be deleted!");
			}
			return true;
		}
		catch(Exception e)
		{
			logger.error("Invalid ID!");
			return false;
		}
	}

	

}
