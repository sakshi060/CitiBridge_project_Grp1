package com.citi.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.citi.demo.model.StockDetails;
import com.citi.demo.model.StockObject;

import yahoofinance.YahooFinance;

@Service
public class StockWrapperServiceImpl {
	static StockDetails stockDetails[];
	static ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
	static ArrayList<StockDetails> finalList=new ArrayList<StockDetails>();
	
	public static StockObject findStock(String companySymbol)
	{
		try
		{
			return new StockObject(YahooFinance.get(companySymbol));
		}
		catch (IOException e)
		{
			System.out.println("Error!");
		}
		return null;
	}
	
	public static StockDetails getStocksDetails(String companySymbol) throws IOException
	{
		StockObject stock = findStock(companySymbol);
		StockDetails stockDetails = new StockDetails();
		try {
			stockDetails = new StockDetails(stock.getCompanySymbol(),stock.getCompanyName(), stock.getOpen(),stock.getClose(),stock.getHigh(),stock.getLow(),stock.getVolume(),stock.getChange(),stock.getPeRatio(),stock.getMarketCap(),stock.getReturnOnEquity(),stock.getHistory());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stockDetails;
		
	}
	
	public static StockDetails findTopPerformingStock(List<String> companySymbols) throws IOException
	{
		stocksList = StockWrapperServiceImpl.findStocksMarketCap(companySymbols);
		for(int i = 0;i<companySymbols.size();i++)
		{
			stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(), stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
			finalList.add(stockDetails[i]);
		}
		return finalList.get(0);
		
	}
	public static ArrayList<StockDetails> findStocksAndSort(List<String> companySymbols, String attribute) throws IOException
	{
		try {
		if(attribute.compareTo("marketCap")==0)
		{
			stocksList = StockWrapperServiceImpl.findStocksMarketCap(companySymbols);
			
			for(int i = 0;i<companySymbols.size();i++)
			{
				stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(), stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
				finalList.add(stockDetails[i]);
			}
			return finalList;

		}
		else if(attribute.compareTo("returnOnEquity")==0)
		{
			stocksList = StockWrapperServiceImpl.findStocksReturnOnEquity(companySymbols);
			for(int i = 0;i<companySymbols.size();i++)
			{
				stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(),stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
				finalList.add(stockDetails[i]);
			}
			return finalList;
		}
		else if(attribute.compareTo("peRatio")==0)
		{
			stocksList = StockWrapperServiceImpl.findStocksPeRatio(companySymbols);
			for(int i = 0;i<companySymbols.size();i++)
			{
				stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(),stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
				finalList.add(stockDetails[i]);
			}
			return finalList;
		}
		else if(attribute.compareTo("change")==0)
		{
			stocksList = StockWrapperServiceImpl.findStocksOnChange(companySymbols);
			for(int i = 0;i<companySymbols.size();i++)
			{
				stockDetails[i] = new StockDetails(stocksList.get(i).getCompanySymbol(),stocksList.get(i).getCompanyName(),stocksList.get(i).getOpen(),stocksList.get(i).getClose(),stocksList.get(i).getHigh(),stocksList.get(i).getLow(),stocksList.get(i).getVolume(),stocksList.get(i).getChange(),stocksList.get(i).getPeRatio(),stocksList.get(i).getMarketCap(),stocksList.get(i).getReturnOnEquity(),stocksList.get(i).getHistory());
				finalList.add(stockDetails[i]);
			}
			return finalList;
		}
		}
		
		catch(Exception e)
		{
			System.out.println("Mentioned Attribute is invalid!");
			return null;
		}
		return null;
		
	}

	
	public BigDecimal findMarketCap(StockObject stock) throws IOException
	{
		return stock.getStock().getStats().getMarketCap();
	}
	
	public static ArrayList<StockObject> findStocksMarketCap(List<String> companySymbols) throws IOException
	{
		try {
			ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
			for (int i = 0; i < companySymbols.size(); i++) {
				findStock(companySymbols.get(i));
			}
			
		stocksList = SortStocks.sort(companySymbols, "marketCap");
		return stocksList;
		}
		catch(Exception e)
		{
			System.out.println("Error!");
			return null;
		}
	}
	
	
	
	public BigDecimal findReturnOnEquity(StockObject stock) throws IOException
	{
		return stock.getStock().getStats().getROE();
	}
	
	public static ArrayList<StockObject> findStocksReturnOnEquity(List<String> companySymbols) throws IOException
	{
		try {
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		for (int i = 0; i < companySymbols.size(); i++) {
			findStock(companySymbols.get(i));
}
		stocksList = SortStocks.sort(companySymbols, "returnOnEquity");
		return stocksList;
		}
		catch(Exception e)
		{
			System.out.println("Error!");
			return null;
		}
	}
	
	
	public BigDecimal findPERatio(StockObject stock) throws IOException
	{
		return stock.getStock().getStats().getPe();
	}
	
	public static ArrayList<StockObject> findStocksPeRatio(List<String> companySymbols) throws IOException
	{
		try {
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		for (int i = 0; i < companySymbols.size(); i++) {
			findStock(companySymbols.get(i));
	}
		stocksList = SortStocks.sort(companySymbols, "peRatio");
		return stocksList;
		}
		catch(Exception e)
		{
			System.out.println("Error!");
			return null;
		}
	}
	
	public BigDecimal findChange(StockObject stock) throws IOException
	{
		return stock.getStock().getQuote().getChange();
	}
	
	public static ArrayList<StockObject> findStocksOnChange(List<String> companySymbols) throws IOException
	{ 
		try {
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		for (int i = 0; i < companySymbols.size(); i++) {
			findStock(companySymbols.get(i));
	}
		stocksList = SortStocks.sort(companySymbols, "change");
		return stocksList;
		}
		catch(Exception e)
		{
			System.out.println("Error!");
			return null;
		}
	}
	
	

}
