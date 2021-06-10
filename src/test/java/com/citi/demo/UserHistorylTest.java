package com.citi.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;


import com.citi.demo.model.StockDetails;
import com.citi.demo.model.UserHistory;
import com.citi.demo.service.StockRecommendationService;
import com.citi.demo.service.UserHistoryService;

import yahoofinance.histquotes.HistoricalQuote;

@SpringBootTest
public class UserHistorylTest {

	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	StockRecommendationService stockRecommendationService;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Test
	public void testsaveUserHistoryByuserId() {
		// TODO Auto-generated method stub
		String userId = "XYZ";
		String companySymbol = "TCS.NS";
		long quantity = 30;
		UserHistory stock = new UserHistory();
		try
		{
			stock = userHistoryService.saveUserHistoryByuserId(userId,companySymbol,quantity);
			if(stock.getCompanySymbol()!=null) {
				logger.info("Stock saved successfully!");
				System.out.println("\n");
				System.out.println("Stock for userId: "+userId+" saved successfully!");
				System.out.println(stock);
				System.out.println("\n");
				assertThat(stock).isNotNull();	
			}
		}
		catch(Exception e)
		{
			logger.info("New Stock could not be added!");
		}
	}

	@Test
	public void testgetUserHistoryByuserId()
	{
		String userId = "Rhythm";
		try
		{
			logger.info("Showing User History of user - " +userId);
			ArrayList<UserHistory> userHistory = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
			System.out.println("\n");
			System.out.println("User History(saved stocks) of: "+userId);
			System.out.println(userHistory);
			System.out.println("\n");
		}
		catch(Exception e)
		{
			logger.info("User not found!");

		}
	}

	@Test
	public void testshowTopPerformingStock() throws IOException 
	{
		String userId = "Rhythm";
		StockDetails topStock = new StockDetails();

		List<String> companySymbols=new ArrayList<String>(); 

		try
		{
			companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
			topStock = stockRecommendationService.getStocksDetails(companySymbols.get(0));
			System.out.println("\n");
			System.out.println("Top Performing Stock for user: "+userId);
			System.out.println(topStock);
			System.out.println("\n");
		}
		catch(Exception e)
		{
			logger.info("User not found!");

		}
	}
	@Test
	public void getCompanySymbolsByUserId(String userId) {
		//Returns Company Symbols of Saved Stocks of userId passed as an argument.
		List<String> companySymbols=new ArrayList<String>();  
		try
		{
			companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
			System.out.println("Company Symbols of Stocks Saved by user:  "+userId);
			System.out.println(companySymbols);
		}
		catch(Exception e)
		{
			logger.info("Sector not found!");
		}
	
	}


	@Test
	public void testdeleteStocks() {
		try {
			int ids[] = {17,18};
			for(int i=0;i<ids.length;i++)
			{
				int deleted = userHistoryService.deleteUserHistoryByuserId(ids[i]);
				if(deleted==1) {
					logger.info("Deleted Stock!");
					System.out.println("\n");
				}
				else
					logger.info("Stock could not be deleted!");
			}
		}
		catch(Exception e)
		{
			logger.info("Invalid ID!");
		}
	}

	@Test
	public void testgetHistoricalData() throws IOException{
		List<HistoricalQuote> list;
		String companySymbol = "TCS.NS";
		try
		{
			list = stockRecommendationService.findStock(companySymbol).getHistory();
			System.out.println("\n");
			System.out.println("Historical Data of: "+companySymbol);
			System.out.println("Company Symbol: "+list.get(0).getSymbol());
			System.out.println("Adj Close: "+list.get(0).getAdjClose());
			System.out.println("Close: "+list.get(0).getClose());
			System.out.println("High: "+list.get(0).getHigh());
			System.out.println("Low: "+list.get(0).getLow());
			System.out.println("Open: "+list.get(0).getOpen());
			System.out.println("Volume: "+list.get(0).getVolume());
			System.out.println("\n");
		}
		catch(Exception e)
		{
			System.out.println("Historical Data could not be found!");
		}
	}
}



