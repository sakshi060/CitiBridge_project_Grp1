package com.citi.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.StockDetails;
import com.citi.demo.model.StockObject;
import com.citi.demo.service.SectorStocksService;
import com.citi.demo.service.StockRecommendationService;

import yahoofinance.histquotes.HistoricalQuote;

@RequestMapping("/stockDetails")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class StockDetailsController {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	StockRecommendationService stockRecommendationService;

	@RequestMapping(value = "/showRecommendedStocks/{sector}/{parameter}", method = RequestMethod.GET)
	public ArrayList<StockDetails> showRecommendedStocks(@PathVariable String sector , @PathVariable String parameter) throws IOException {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		ArrayList<StockDetails> finalList=new ArrayList<StockDetails>();

		try
		{
			logger.info("Getting Recommendations for Sector - {} and Parameter - {}",sector,parameter);
			finalList = stockRecommendationService.findStocksAndSort(sector, parameter);
		}
		catch(Exception e)
		{
			logger.error("Sector not found!");
		}
		return finalList;
	}

	@RequestMapping(value = "/showStockDetails/{companySymbol}", method = RequestMethod.GET)
	public StockDetails showStockDetails(@PathVariable String companySymbol ) {
		// Returns Stock Details of companySymbol passed as an argument.
		StockDetails stockDetails = new StockDetails();
		try
		{
			logger.info("Details of:  - " +companySymbol);
			stockDetails = stockRecommendationService.getStocksDetails(companySymbol);
		}
		catch(Exception e)
		{
			logger.error("Company Symbol not found!");
		}
		return stockDetails;
	}

	@RequestMapping(value = "/showStockHistory/{companySymbol}", method = RequestMethod.GET)
	public List<HistoricalQuote> getHistory(@PathVariable String companySymbol) throws IOException
	{
		// Returns History of companySymbol passed as an argument.
		StockObject stock = new StockObject();
		try
		{
			stock = stockRecommendationService.findStock(companySymbol);
			logger.info("History of "+companySymbol+ "found!");
		}
		catch(Exception e)
		{
			logger.error("History of "+companySymbol+ " not found!");
		}
		return stock.getHistory();
	}
	
	@RequestMapping(value = "/historicalData/{companySymbol}", method = RequestMethod.GET)
	public List<HistoricalQuote> getHistoricalData(@PathVariable String companySymbol) throws IOException{
		//Returns Historical Data of companySymbol passed as an argument.
		try
		{
			return stockRecommendationService.findStock(companySymbol).getHistory();
		}
		catch(Exception e)
		{
			logger.error("Stock of: "+companySymbol+ " not found!");
			return null;
		}

	}


}
