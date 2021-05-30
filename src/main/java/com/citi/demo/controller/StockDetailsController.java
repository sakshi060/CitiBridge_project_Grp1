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
import com.citi.demo.service.StockWrapperServiceImpl;

import yahoofinance.histquotes.HistoricalQuote;

@RequestMapping("/sort")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class StockDetailsController {
	
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService dao1;
	StockWrapperServiceImpl stockWrapper;

	@RequestMapping(value = "/showsSortedData/{sector}/{parameter}", method = RequestMethod.GET)
	public ArrayList<StockDetails> showSortedStocks(@PathVariable String sector , @PathVariable String parameter) {

		List<String> companySymbols=new ArrayList<String>(); 
		ArrayList<StockDetails> finalList=new ArrayList<StockDetails>();
		
		try
		{
			logger.info("Showing Companies of Sector - " +sector);

			companySymbols =  dao1.findCompanySymbolBySector(sector);
			finalList = StockWrapperServiceImpl.findStocksAndSort(companySymbols, parameter);
		
			return finalList;
		}
		
		catch(Exception e)
		{
			logger.info("Sector not found!");
			return finalList;
		}

	}

	@RequestMapping(value = "/showsStockDetails/{companySymbol}", method = RequestMethod.GET)
	public StockDetails showStockDetails(@PathVariable String companySymbol ) {
		StockDetails stockDetails = new StockDetails();

		try
		{
			logger.info("Showing Details of:  - " +companySymbol);
			stockDetails = StockWrapperServiceImpl.getStocksDetails(companySymbol);
			return stockDetails;
		}

		catch(Exception e)
		{
			logger.info("Company Symbol not found!");
			return null;
		}
	}
	
	@RequestMapping(value = "/StockDetails/{companySymbol}", method = RequestMethod.GET)
	
	public List<HistoricalQuote> getHistory(@PathVariable String companySymbol) throws IOException
	{
		StockObject stock = new StockObject();
		stock = StockWrapperServiceImpl.findStock(companySymbol);
		return stock.getHistory();
	}


}
