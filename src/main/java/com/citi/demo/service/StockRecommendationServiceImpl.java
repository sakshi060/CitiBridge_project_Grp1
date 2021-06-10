package com.citi.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.StockDetails;
import com.citi.demo.model.StockObject;
import com.citi.demo.util.SortingParameterList;

import yahoofinance.YahooFinance;



@Service
public class StockRecommendationServiceImpl implements StockRecommendationService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	SortStocksService sortStocks;
	@Autowired
	StockRecommendationService stockRecommendationService;
	
	@Override
	public StockObject findStock(String companySymbol) throws IOException
	{
		//Finds and Returns Stock for a given companySymbol passed as an argument from Yahoo Finance API.
	
		try
		{
				StockObject stockObject = new StockObject();
				System.out.println(YahooFinance.get(companySymbol));
				stockObject.stock = YahooFinance.get(companySymbol);
				return stockObject;	
		}
		catch (IOException e)
		{
			logger.error("Stock not found!");
		}
		return null;
	}

	@Override
	public StockDetails getStocksDetails(String companySymbol)
	{
		//Finds Stock for a given companySymbol passed as an argument from Yahoo Finance API and returns its Details.

		StockDetails stockDetails = new StockDetails();
		try {

			StockObject stock = stockRecommendationService.findStock(companySymbol);
			stockDetails.setCompanySymbol(stock.getCompanySymbol());
			stockDetails.setCompanyName(stock.getCompanyName());
			stockDetails.setOpen(stock.getOpen());
			stockDetails.setClose(stock.getClose());
			stockDetails.setHigh(stock.getHigh());
			stockDetails.setLow(stock.getLow());
			stockDetails.setVolume(stock.getVolume());
			stockDetails.setChange(stock.getChange());
			stockDetails.setPeRatio(stock.getPeRatio());
			stockDetails.setMarketCap(stock.getMarketCap());
			stockDetails.setHistory(stock.getHistory());
			logger.info("Stock Details of Company Symbol: "+companySymbol+ " found!");
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			logger.error("Stock Details not found!");
		}
		return stockDetails;
	}

	@Override
	public StockDetails findTopPerformingStock(List<String> companySymbols)
	{
		//Finds Top Performing Stock from the given list of Companies(companySymbols) passed as an argument.

		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		ArrayList<StockDetails> sortedStocksList=new ArrayList<StockDetails>();

		try {
			stocksList = stockRecommendationService.findStocksOnChange(companySymbols);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		for(int i = 0;i<companySymbols.size();i++)
		{
			try {
				StockDetails stockDetails = new StockDetails();
				stockDetails.setCompanySymbol(stocksList.get(i).getCompanySymbol());
				stockDetails.setCompanyName(stocksList.get(i).getCompanyName());
				stockDetails.setOpen(stocksList.get(i).getOpen());
				stockDetails.setClose(stocksList.get(i).getClose());
				stockDetails.setHigh(stocksList.get(i).getHigh());
				stockDetails.setLow(stocksList.get(i).getLow());
				stockDetails.setVolume(stocksList.get(i).getVolume());
				stockDetails.setChange(stocksList.get(i).getChange());	
				stockDetails.setPeRatio(stocksList.get(i).getPeRatio());
				stockDetails.setMarketCap(stocksList.get(i).getMarketCap());
				stockDetails.setHistory(stocksList.get(i).getHistory());
				sortedStocksList.add(stockDetails);
				logger.info("Top Performing Stock found!");	
			} 
			catch (IOException e) {
				logger.error("Top Performing Stock not found!");
				e.printStackTrace();
			}
		}
		return sortedStocksList.get(0);
	}

	@Override
	public  ArrayList<StockDetails> findStocksAndSort(String sector, String attribute) 
	{
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		ArrayList<StockDetails> sortedStocksList=new ArrayList<StockDetails>();
		ArrayList<StockDetails> topStocksList=new ArrayList<StockDetails>();
		List<String> companySymbols = new ArrayList<String>();
		try {
			companySymbols =  sectorStocksService.getCompanySymbolBySector(sector);
			if(attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)
			{
				logger.info("Sorting on Market Capital");
				stocksList = stockRecommendationService.findStocksOnMarketCap(companySymbols);
				for(int i = 0;i<companySymbols.size();i++)
				{
					StockDetails stockDetails = new StockDetails();
					stockDetails.setCompanySymbol(stocksList.get(i).getCompanySymbol());
					stockDetails.setCompanyName(stocksList.get(i).getCompanyName());
					stockDetails.setOpen(stocksList.get(i).getOpen());
					stockDetails.setClose(stocksList.get(i).getClose());
					stockDetails.setHigh(stocksList.get(i).getHigh());
					stockDetails.setLow(stocksList.get(i).getLow());
					stockDetails.setVolume(stocksList.get(i).getVolume());
					stockDetails.setChange(stocksList.get(i).getChange());	
					stockDetails.setPeRatio(stocksList.get(i).getPeRatio());
					stockDetails.setMarketCap(stocksList.get(i).getMarketCap());
					stockDetails.setHistory(stocksList.get(i).getHistory());
					sortedStocksList.add(stockDetails);
				}
				if(sortedStocksList.size()>5)
				{
					for(int i =0; i<5;i++)
					{
						topStocksList.add(sortedStocksList.get(i));
					}
				}
				else
					topStocksList = sortedStocksList;
			}
			else if(attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0)
			{
				logger.info("Sorting on PE Ratio");
				stocksList = stockRecommendationService.findStocksOnPeRatio(companySymbols);
				for(int i = 0;i<companySymbols.size();i++)
				{
					StockDetails stockDetails = new StockDetails();
					stockDetails.setCompanySymbol(stocksList.get(i).getCompanySymbol());
					stockDetails.setCompanyName(stocksList.get(i).getCompanyName());
					stockDetails.setOpen(stocksList.get(i).getOpen());
					stockDetails.setClose(stocksList.get(i).getClose());
					stockDetails.setHigh(stocksList.get(i).getHigh());
					stockDetails.setLow(stocksList.get(i).getLow());
					stockDetails.setVolume(stocksList.get(i).getVolume());
					stockDetails.setChange(stocksList.get(i).getChange());	
					stockDetails.setPeRatio(stocksList.get(i).getPeRatio());
					stockDetails.setMarketCap(stocksList.get(i).getMarketCap());
					stockDetails.setHistory(stocksList.get(i).getHistory());
					sortedStocksList.add(stockDetails);
				}
				if(sortedStocksList.size()>5)
				{
					for(int i =0; i<5;i++)
					{
						topStocksList.add(sortedStocksList.get(i));
					}
				}
				else
					topStocksList = sortedStocksList;
			}
			else if(attribute.compareTo(SortingParameterList.CHANGE.toString())==0)
			{
				logger.info("Sorting on Change");
				stocksList = stockRecommendationService.findStocksOnChange(companySymbols);
				for(int i = 0;i<stocksList.size();i++)
				{
					StockDetails stockDetails = new StockDetails();
					stockDetails.setCompanySymbol(stocksList.get(i).getCompanySymbol());
					stockDetails.setCompanyName(stocksList.get(i).getCompanyName());
					stockDetails.setOpen(stocksList.get(i).getOpen());
					stockDetails.setClose(stocksList.get(i).getClose());
					stockDetails.setHigh(stocksList.get(i).getHigh());
					stockDetails.setLow(stocksList.get(i).getLow());
					stockDetails.setVolume(stocksList.get(i).getVolume());
					stockDetails.setChange(stocksList.get(i).getChange());	
					stockDetails.setPeRatio(stocksList.get(i).getPeRatio());
					stockDetails.setMarketCap(stocksList.get(i).getMarketCap());
					stockDetails.setHistory(stocksList.get(i).getHistory());
					sortedStocksList.add(stockDetails);
				}

				if(sortedStocksList.size()>5)
				{
					for(int i =0; i<5;i++)
					{
						topStocksList.add(sortedStocksList.get(i));
					}
				}
				else
					topStocksList = sortedStocksList;

				if(topStocksList.size()!=0 && (attribute.compareTo(SortingParameterList.CHANGE.toString())==0) || (attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0) || (attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0))
					logger.info("Mentioned Attribute found!");
				
	
				return topStocksList;
			}
		}
		catch(Exception e)
		{
			logger.error("Sorting could not be done");
			return topStocksList;
		}
		return topStocksList;
		
	}

	@Override
	public  ArrayList<StockObject> findStocksOnMarketCap(List<String> companySymbols)
	{
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.MARKET_CAP.toString());
			if(stocksList.size()!=0)
			logger.info("Sorting done on the basis of Market Capital");
			return stocksList;
		}
		catch(Exception e)
		{
			logger.error("Error occured while sorting on the basis of Market Capital");
			return null;
		}
	}

	@Override
	public  ArrayList<StockObject> findStocksOnPeRatio(List<String> companySymbols)
	{
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.PE_RATIO.toString());
			if(stocksList.size()!=0)
			logger.info("Sorting done on the basis of PE Ratio");
			return stocksList;
		}
		catch(Exception e)
		{
			logger.error("Error occured while sorting on the basis of PE Ratio");
			return null;
		}
	}

	@Override
	public  ArrayList<StockObject> findStocksOnChange(List<String> companySymbols)
	{ 
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.CHANGE.toString());
			if(stocksList.size()!=0)
			logger.info("Sorting done on the basis of Change");
			return stocksList;
		}
		catch(Exception e)
		{
			logger.error("Error occured while sorting on the basis of Change",e.getMessage());
			return null;
		}
	}
}
