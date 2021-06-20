package com.citi.trade.recommendation.controller;

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

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;
import com.citi.trade.recommendation.service.SectorStocksService;
import com.citi.trade.recommendation.service.StockDetailsService;

import yahoofinance.histquotes.HistoricalQuote;

@RequestMapping("/stockDetails")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class StockDetailsController {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	StockDetailsService stockDetailsService;

	@RequestMapping(value = "/showRecommendedStocks/{sector}/{parameter}", method = RequestMethod.GET)
	public List<StockDetails> showRecommendedStocks(@PathVariable String sector , @PathVariable String parameter) throws IOException {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		logger.info("Getting Stocks Recommendation for Sector: {} and Parameter: {}" ,sector,parameter);
		return  stockDetailsService.findStocksAndSort(sector, parameter);
	}

	@RequestMapping(value = "/showStockDetails/{companySymbol}", method = RequestMethod.GET)
	public StockDetails showStockDetails(@PathVariable String companySymbol ) {
		// Returns Stock Details of companySymbol passed as an argument.
		
		logger.info("Getting Stock Details of Company: {}" ,companySymbol);
		return stockDetailsService.getStocksDetails(companySymbol);
	}

	@RequestMapping(value = "/showStockHistory/{companySymbol}", method = RequestMethod.GET)
	public List<HistoricalQuote> getHistory(@PathVariable String companySymbol) {
		// Returns History of companySymbol passed as an argument.

		logger.info("Getting Stock History of Company: {}" ,companySymbol);
		List<HistoricalQuote> history = new ArrayList<>();
		try {
			StockObject stock = stockDetailsService.findStock(companySymbol);
			history = stock.getHistory();
		} catch( Exception e) {
			logger.error("Error in getting history {}", e.getMessage());
		}
		return history;
	}

}
