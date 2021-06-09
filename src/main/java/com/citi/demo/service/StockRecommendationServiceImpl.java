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
	public StockObject findStock(String companySymbol)
	{
		//Finds and Returns Stock for a given companySymbol passed as an argument from Yahoo Finance API.
		try
		{
			return new StockObject(YahooFinance.get(companySymbol));
		}
		catch (IOException e)
		{
			logger.info("Stock not found!");;
		}
		return null;
	}

	@Override
	public StockDetails getStocksDetails(String companySymbol)
	{
		//Finds Stock for a given companySymbol passed as an argument from Yahoo Finance API and returns its Details.
		StockObject stock = stockRecommendationService.findStock(companySymbol);
		StockDetails stockDetails = new StockDetails();
		try {
			stockDetails = new StockDetails(stock.getCompanySymbol(),stock.getCompanyName(), stock.getOpen(),stock.getClose(),stock.getHigh(),stock.getLow(),stock.getVolume(),stock.getChange(),stock.getPeRatio(),stock.getMarketCap(),stock.getReturnOnEquity(),stock.getHistory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stockDetails;
	}

	@Override
	public StockDetails findTopPerformingStock(List<String> companySymbols)
	{
		//Finds Top Performing Stock from the given list of Companies(companySymbols) passed as an argument.
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		ArrayList<StockDetails> sortedstockslist=new ArrayList<StockDetails>();
		
		StockDetails stockDetails[] = new StockDetails[companySymbols.size()];
		try {
			stocksList = stockRecommendationService.findStocksOnChange(companySymbols);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0;i<companySymbols.size();i++)
		{
			try {
				stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(), stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sortedstockslist.add(stockDetails[i]);
		}
		return sortedstockslist.get(0);
	}

	@Override
	public  ArrayList<StockDetails> findStocksAndSort(String sector, String attribute) 
	{
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		ArrayList<StockDetails> sortedstockslist=new ArrayList<StockDetails>();
		ArrayList<StockDetails> topstockslist=new ArrayList<StockDetails>();
		List<String> companySymbols = new ArrayList<String>();
		companySymbols =  sectorStocksService.getCompanySymbolBySector(sector);
		
		StockDetails stockDetails[] = new StockDetails[companySymbols.size()];
		try {
			if(attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)
			{
				logger.info("Sorting on Market Capital");
				stocksList = stockRecommendationService.findStocksOnMarketCap(companySymbols);
				for(int i = 0;i<companySymbols.size();i++)
				{
					stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(), stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
					sortedstockslist.add(stockDetails[i]);
				}
				if(sortedstockslist.size()>5)
				{
					for(int i =0; i<5;i++)
					{
						topstockslist.add(sortedstockslist.get(i));
					}
				}
				else
					topstockslist = sortedstockslist;
			}
			else if(attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0)
			{
				logger.info("Sorting on PE Ratio");
				System.out.println(attribute);
				stocksList = stockRecommendationService.findStocksOnPeRatio(companySymbols);
				System.out.println(stocksList);
				for(int i = 0;i<companySymbols.size();i++)
				{
					stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(),stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
					sortedstockslist.add(stockDetails[i]);
				}
				if(sortedstockslist.size()>5)
				{
					for(int i =0; i<5;i++)
					{
						topstockslist.add(sortedstockslist.get(i));
					}
				}
				else
					topstockslist = sortedstockslist;
			}
			else if(attribute.compareTo(SortingParameterList.CHANGE.toString())==0)
			{
				logger.info("Sorting on Change");
				stocksList = stockRecommendationService.findStocksOnChange(companySymbols);
				for(int i = 0;i<companySymbols.size();i++)
				{
					stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(),stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
					sortedstockslist.add(stockDetails[i]);
				}
				System.out.println(sortedstockslist);
				if(sortedstockslist.size()>5)
					for(int i =0; i<5;i++)
					{
						topstockslist.add(sortedstockslist.get(i));
					}
			}
			else
				topstockslist = sortedstockslist;
			logger.info("Mentioned Attribute found!");
			return topstockslist;
		}

		catch(Exception e)
		{
			logger.info("Mentioned Attribute can not be found!");
			return topstockslist;
		}

	}

	@Override
	public  ArrayList<StockObject> findStocksOnMarketCap(List<String> companySymbols)
	{
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			for (int i = 0; i < companySymbols.size(); i++) {
				findStock(companySymbols.get(i));
			}
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.MARKET_CAP.toString());
			return stocksList;
		}
		catch(Exception e)
		{
			logger.info("Error");
			return null;
		}
	}

	@Override
	public  ArrayList<StockObject> findStocksOnPeRatio(List<String> companySymbols)
	{
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			for (int i = 0; i < companySymbols.size(); i++) {
				findStock(companySymbols.get(i));
			}
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.PE_RATIO.toString());
			return stocksList;
		}
		catch(Exception e)
		{
			logger.info("Error");
			return null;
		}
	}

	@Override
	public  ArrayList<StockObject> findStocksOnChange(List<String> companySymbols)
	{ 
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			for (int i = 0; i < companySymbols.size(); i++) {
				findStock(companySymbols.get(i));
			}
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.CHANGE.toString());
			return stocksList;
		}
		catch(Exception e)
		{
			System.out.println("Error!");
			return null;
		}
	}



}
