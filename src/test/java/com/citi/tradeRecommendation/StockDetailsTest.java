package com.citi.tradeRecommendation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.tradeRecommendation.model.StockDetails;
import com.citi.tradeRecommendation.model.StockObject;
import com.citi.tradeRecommendation.service.SectorStocksService;
import com.citi.tradeRecommendation.service.StockDetailsService;


@SpringBootTest
public class StockDetailsTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	StockDetailsService stockDetailsService;
	
	@Test
	public void testShowRecommendedStocks() {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		ArrayList<StockDetails> finalList=new ArrayList<StockDetails>();
		String sector = "FINANCIAL SERVICES";
		String parameter = "MARKET_CAP";
		try
		{
			logger.info("Companies under Sector - " +sector);
			finalList = stockDetailsService.findStocksAndSort(sector, parameter);
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
			stockDetails = stockDetailsService.getStocksDetails(companySymbol);
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
			stock = stockDetailsService.findStock(companySymbol);
			logger.info("History of "+companySymbol+ "found!");
		}
		catch(Exception e)
		{
			logger.error("History of "+companySymbol+ " not found!");
		}
		System.out.println(stock.getHistory());
	}
	
	@Test
	public void getTopPerformingStock()
	{
		List<String> companySymbols = new ArrayList<String>();

		companySymbols.add("SUNPHARMA.NS");
		companySymbols.add("ULTRACEMCO.NS");
		companySymbols.add("ONGC.NS");
		companySymbols.add("POWERGRID.NS");
		companySymbols.add("NESTLEIND.NS");
		companySymbols.add("INDUSINDBK.NS");
		companySymbols.add("DRREDDY.NS");
		companySymbols.add("AXISBANK.NS");
		companySymbols.add("HDFCBANK.NS");
		companySymbols.add("JSWSTEEL.NS");
		StockDetails stockDetails = new StockDetails();
		stockDetails = stockDetailsService.findTopPerformingStock(companySymbols);
		System.out.println("\n");
		System.out.println("Top Performing Stock :-"+stockDetails);
		System.out.println("\n");
	}



}


