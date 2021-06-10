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
			if(finalList.size()!=0)
				logger.info("Showing Recommendations");
			else
				logger.error("Recommendations not found!");
		}
		catch(Exception e)
		{
			logger.error("Recommendations not found!",e.getMessage());
		}
		return finalList;
	}

	@RequestMapping(value = "/showStockDetails/{companySymbol}", method = RequestMethod.GET)
	public StockDetails showStockDetails(@PathVariable String companySymbol ) {
		// Returns Stock Details of companySymbol passed as an argument.
		
		StockDetails stockDetails = new StockDetails();
		try
		{
			logger.info("Getting Stock Details for Company - {}",companySymbol);
			stockDetails = stockRecommendationService.getStocksDetails(companySymbol);
			if(stockDetails!=null)
				logger.info("Showing Stock Details of {}"+companySymbol);
			else
				logger.error("Stock Details not found!");
		}
		catch(Exception e)
		{
			logger.error("Stock Details not found!",e.getMessage());
		}
		return stockDetails;
	}

	@RequestMapping(value = "/showStockHistory/{companySymbol}", method = RequestMethod.GET)
	public List<HistoricalQuote> getHistory(@PathVariable String companySymbol) throws NullPointerException
	{
		// Returns History of companySymbol passed as an argument.

		StockObject stock = new StockObject();
		try {
			logger.info("Getting Stock History of "+companySymbol);
			stock = stockRecommendationService.findStock(companySymbol);
			if(stock.getHistory()!= null)
			{
				logger.info("Showing History of Stock {} "+companySymbol);
				return stock.getHistory();
			}
			else
				logger.error("History of "+companySymbol+ " not found!");
				return null;
		} catch (NullPointerException | IOException e) {
			logger.error("History of "+companySymbol+ " not found!",e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

}
