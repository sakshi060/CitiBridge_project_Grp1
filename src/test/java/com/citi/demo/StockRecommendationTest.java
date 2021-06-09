package com.citi.demo;

import java.io.IOException;
import java.util.ArrayList;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.citi.demo.model.StockDetails;
import com.citi.demo.model.StockObject;
import com.citi.demo.service.SectorStocksService;
import com.citi.demo.service.StockRecommendationService;


@SpringBootTest
public class StockRecommendationTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorstocksService;
	@Autowired
	StockRecommendationService stockRecommendationService;
	
	@Test
	public void testShowRecommendedStocks() {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		ArrayList<StockDetails> finalList=new ArrayList<StockDetails>();
		String sector = "FINANCIAL SERVICES";
		String parameter = "MARKET_CAP";
		try
		{
			logger.info("Companies under Sector - " +sector);
			finalList = stockRecommendationService.findStocksAndSort(sector, parameter);
		}
		catch(Exception e)
		{
			logger.error("Sector not found!");
		}
		System.out.println(finalList);
	}

	@Test
	public void testShowStockDetails() {
		// Returns Stock Details of companySymbol passed as an argument.
		StockDetails stockDetails = new StockDetails();
		String companySymbol = "RELIANCE.NS";
		try
		{
			logger.info("Details of:  - " +companySymbol);
			stockDetails = stockRecommendationService.getStocksDetails(companySymbol);
		}
		catch(Exception e)
		{
			logger.error("Company Symbol not found!");
		}
		System.out.println(stockDetails);
	}

	@Test
	public void getHistory() throws IOException
	{
		// Returns History of companySymbol passed as an argument.
		StockObject stock = new StockObject();
		String companySymbol = "BPCL.NS";
		try
		{
			stock = stockRecommendationService.findStock(companySymbol);
			logger.info("History of "+companySymbol+ "found!");
		}
		catch(Exception e)
		{
			logger.error("History of "+companySymbol+ " not found!");
		}
		System.out.println(stock.getHistory());
	}


}


