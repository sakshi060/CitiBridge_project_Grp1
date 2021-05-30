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
import com.citi.demo.service.StockWrapperServiceImpl;
import com.citi.demo.service.UserHistoryService;

import yahoofinance.histquotes.HistoricalQuote;


@RequestMapping("/userhistory")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class UserHistoryController {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	UserHistoryService dao;
	@Autowired
	SectorStocksService sector;

	@PostMapping("/saveStocks/{userId}/{companySymbol}/{quantity}")
	public UserHistory saveStocks(@PathVariable String userId , @PathVariable String companySymbol, @PathVariable long quantity) {

		UserHistory share = new UserHistory();
		try
		{
			share = dao.saveUserHistoryByuserId(userId,companySymbol,quantity);
			if(share.getCompanySymbol()!=null) {
				logger.info("Stock saved successfully!");
				return share;
			}
		}
		catch(Exception e)
		{
			logger.info("New Company could not be added!");
		}

		return new UserHistory();
	}

	@RequestMapping(value = "/showStocks/{userId}", method = RequestMethod.GET)
	public ArrayList<UserHistory> showShares(@PathVariable String userId) {

		ArrayList<UserHistory> stocks = new ArrayList<UserHistory>();

		try
		{
			logger.info("Showing User History of user - " +userId);
			stocks = (ArrayList<UserHistory>) dao.getUserHistoryByuserId(userId);
			return stocks;

		}
		catch(Exception e)
		{
			logger.info("User not found!");
			return null;
		}

	}

	@RequestMapping(value = "/showTopPerformingStock/{userId}", method = RequestMethod.GET)
	public StockDetails showTopPerformingStock(@PathVariable String userId) throws IOException 
	{
		StockDetails topStock = new StockDetails();

		List<String> companySymbols=new ArrayList<String>(); 

		try {

			companySymbols =  dao.getCompanySymbolsByUserId(userId);
			topStock = StockWrapperServiceImpl.getStocksDetails(companySymbols.get(0));
			return topStock;
		}
		catch(Exception e)
		{
			logger.info("User not found!");
			return null;
		}
	}

	public List<String> getCompanySymbolsByUserId(String userId) {
		List<String> companySymbols=new ArrayList<String>();  
		try
		{
			companySymbols =  dao.getCompanySymbolsByUserId(userId);
			return companySymbols;

		}
		catch(Exception e)
		{
			logger.info("Sector not found!");
			return null;
		}

	}

	@RequestMapping(value = "/deleteStocks", method = RequestMethod.POST)
	public boolean deleteStocks( @RequestBody int[] ids) {
		try {
			System.out.println(ids[0]);
			for(int i=0;i<ids.length;i++)
			{
				int deleted = dao.deleteUserHistoryByuserId(ids[i]);
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
			logger.info("Invalid ID!");
			return false;
		}
	}

	@RequestMapping(value = "/historicalData/{companySymbol}", method = RequestMethod.GET)
	public List<HistoricalQuote> HistoricalData(@PathVariable String companySymbol) throws IOException{
		
		return StockWrapperServiceImpl.findStock(companySymbol).getHistory();
		
		
	}
	
}
