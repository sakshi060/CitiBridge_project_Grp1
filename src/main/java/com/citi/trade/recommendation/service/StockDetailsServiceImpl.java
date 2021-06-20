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
		List<StockObject> sectorWiseStocks = new ArrayList<>();
		try
		{
			if(!ObjectUtils.isEmpty(symbols)) {
				int i=0;
				for(String symbol : symbols) {
					allSymbols[i] = symbol;
					i++;
				}
			}
			List<Stock> apiStockDetails = new ArrayList<>(YahooFinance.get(allSymbols).values()); //get data for all stocks from Yahoo API
			for(int i = 0;i<apiStockDetails.size();i++)
			{
				StockObject stock = new StockObject();
				stock.setStock(apiStockDetails.get(i));
				sectorWiseStocks.add(stock);
			}

		}
		catch(Exception e)
		{
			logger.error("Exception caught in findAllStocks {}", e.getMessage());
		}
		return  sectorWiseStocks;
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
	public  List<StockDetails> findStocksAndSort(String sector, String attribute) {
		//Gets Stock from Yahoo API and sorts based on the sector and parameter mentioned

		List<StockObject> stocksList;
		List<StockDetails> sortedStocksList=new ArrayList<>();
		List<String> companySymbols;
//		List<String> enumNames = Stream.of(SortingParameterList.values())
//				.map(SortingParameterList::name)
//				.collect(Collectors.toList());
		if((attribute.compareTo(SortingParameterList.CHANGE.toString())!=0)
				&& (attribute.compareTo(SortingParameterList.PE_RATIO.toString())!=0)
				&& (attribute.compareTo(SortingParameterList.MARKET_CAP.toString())!=0))
		{
			logger.info("Mentioned Attribute: {} not found!",attribute);
			
			return sortedStocksList;
		}
		

		try {
			companySymbols =  sectorStocksService.getCompanySymbolBySector(sector);

			if(attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)
			{
				logger.info("Sorting on the basis of Market Capital");
				stocksList = sortStocks.sort(companySymbols, SortingParameterList.MARKET_CAP.toString());
				sortedStocksList = setAttributesofTop5Stocks(stocksList);
			}
			else if(attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0)
			{
				logger.info("Sorting on the basis of PE Ratio");
				stocksList = sortStocks.sort(companySymbols, SortingParameterList.PE_RATIO.toString());
				sortedStocksList = setAttributesofTop5Stocks(stocksList);

			}
			else if(attribute.compareTo(SortingParameterList.CHANGE.toString())==0)
			{
				logger.info("Sorting on the basis of Change");
				stocksList = sortStocks.sort(companySymbols, SortingParameterList.CHANGE.toString());
				sortedStocksList = setAttributesofTop5Stocks(stocksList);
			}

		}
		catch(Exception e)
		{
			logger.error("Sorting could not be done!");
		}
		return sortedStocksList;
		
	}

 public List<StockDetails> setAttributesofTop5Stocks(List<StockObject> stocksList){
	 List<StockDetails> sortedStocksList=new ArrayList<>();
	 try {
		 if (!stocksList.isEmpty()) {
			 int size = (stocksList.size() >= 5) ? 5 : stocksList.size(); //incase sector has less than 5 stocks
			 for (int i = 0; i < size; i++) {
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

		 }
	 } catch (Exception e) {
	 	logger.error("Error in setAttributesofTop5Stocks {}", e);
	 }
	 return sortedStocksList;
 }
}
