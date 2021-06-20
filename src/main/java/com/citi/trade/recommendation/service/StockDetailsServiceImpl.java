package com.citi.trade.recommendation.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;
import com.citi.trade.recommendation.util.SortingParameterList;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;



@Service
public class StockDetailsServiceImpl implements StockDetailsService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;
	@Autowired
	SortStocksService sortStocks;
	@Autowired
	StockDetailsService stockDetailsService;

	@Override
	public StockObject findStock(String companySymbol) throws IOException {
		//Finds and Returns Stock for a given companySymbol passed as an argument from Yahoo Finance API.

		try
		{
			StockObject stockObject = new StockObject();
			stockObject.setStock(YahooFinance.get(companySymbol));
			return stockObject;	
		}
		catch (IOException e)
		{
			logger.error("Stock not found!");
		}
		return null;
	}

	@Override
	public List<StockObject> findAllStock(List<String> symbols) throws IOException {
		String[] allSymbols = new String[symbols.size()];
		try
		{
			if(!ObjectUtils.isEmpty(symbols)) {
				int i=0;
				for(String symbol : symbols) {
					allSymbols[i] = symbol;
					i++;
				}
			}

			List<Stock> apiStockDetails = new ArrayList<>(YahooFinance.get(allSymbols).values());

			List<StockObject> sectorWiseStocks = new ArrayList<>();
			for(int i = 0;i<apiStockDetails.size();i++)
			{
				StockObject stock = new StockObject();
				stock.setStock(apiStockDetails.get(i));
				sectorWiseStocks.add(stock);
			}
			return  sectorWiseStocks;
		}
		catch(Exception e)
		{
			return null;
		}

	}

	@Override
	public StockDetails getStocksDetails(String companySymbol) {
		//Finds Stock for a given companySymbol passed as an argument from Yahoo Finance API and returns its Details.

		StockDetails stockDetails = new StockDetails();
		try {

			StockObject stock = stockDetailsService.findStock(companySymbol);
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
			logger.info("Stock Details of Company Symbol: {} found!",companySymbol);

		} 
		catch (IOException e) {
			e.printStackTrace();
			logger.error("Stock Details not found!");
		}
		return stockDetails;
	}

	@Override
	public StockDetails findTopPerformingStock(List<String> companySymbols) {
		//Finds Top Performing Stock from the given list of Companies(companySymbols) passed as an argument.

		List<StockObject> stocksList=new ArrayList<>();
		List<StockDetails> sortedStocksList=new ArrayList<>();
		StockDetails topPerformingStock;
		try {
			stocksList = sortStocks.sort(companySymbols, SortingParameterList.MARKET_CAP.toString());
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
		topPerformingStock = sortedStocksList.get(0);
		return topPerformingStock;
	}

	@Override
	public  ArrayList<StockDetails> findStocksAndSort(String sector, String attribute) {
		//Gets Stock from Yahoo API and sorts based on the sector and parameter mentioned

		List<StockObject> stocksList=new ArrayList<>();
		List<StockDetails> sortedStocksList=new ArrayList<>();
		List<StockDetails> topStocksList=new ArrayList<>();
		List<String> companySymbols = new ArrayList<>();

		try {
			companySymbols =  sectorStocksService.getCompanySymbolBySector(sector);

			if((attribute.compareTo(SortingParameterList.CHANGE.toString())==0) || (attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0) || (attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0))
			{
				logger.info("Mentioned Attribute: {} found!",attribute);
			}
			else if(companySymbols==null && (attribute.compareTo(SortingParameterList.CHANGE.toString())==0) || (attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0) || (attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)!=true)
			{
				logger.error("Mentioned Attribute: {} not found!",attribute);
				return topStocksList;
			}	
			else
			{
				logger.error("Mentioned Attribute not found!");
				return topStocksList;
			}

			if(attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)
			{
				logger.info("Sorting on the basis of Market Capital");
				stocksList = sortStocks.sort(companySymbols, SortingParameterList.MARKET_CAP.toString());
				if(stocksList!=null && stocksList.size()!=0)
				{	
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
					logger.info("Sorted on the basis of Market Capital");
				}
				if(sortedStocksList!=null && sortedStocksList.size()>5)
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
				logger.info("Sorting on the basis of PE Ratio");
				stocksList = sortStocks.sort(companySymbols, SortingParameterList.PE_RATIO.toString());
				if(stocksList!=null && stocksList.size()!=0)
				{	
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
					logger.info("Sorted on the basis of PE Ration");
				}

				if(sortedStocksList!=null && sortedStocksList.size()>5)
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
				logger.info("Sorting on the basis of Change");
				stocksList = sortStocks.sort(companySymbols, SortingParameterList.CHANGE.toString());
				if(stocksList!=null && stocksList.size()!=0)
				{	
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
					logger.info("Sorted on the basis of Change");
				}
				if(sortedStocksList!=null && sortedStocksList.size()>5)
				{
					for(int i =0; i<5;i++)
					{
						topStocksList.add(sortedStocksList.get(i));
					}
				}
				else
					topStocksList = sortedStocksList;
			}

			if(topStocksList!=null && topStocksList.size()!=0)
			{
				return topStocksList;
			}

		}
		catch(Exception e)
		{
			logger.error("Sorting could not be done!");
			return topStocksList;
		}
		return topStocksList;


	}


}
