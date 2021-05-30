package com.citi.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.StockObject;


@Service
public class SortStocks {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	public static ArrayList<StockObject> sort(List<String> companySymbols, String attribute) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		if(attribute.compareTo("marketCap")==0)
		{
			try
			{
				System.out.println("Sorting on marketCap");

				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = StockWrapperServiceImpl.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}

				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getMarketCap).reversed())      
						.collect(Collectors.toList());
				System.out.println("\n\t Sorted Stocks");

				for(int i = 0; i < sortedstocks.size(); i++) {   
					System.out.println("\n\t "+sortedstocks.get(i).getCompanySymbol());
				} 

				logger.info("Sorted stocks on Market Cap");
				return (ArrayList<StockObject>) sortedstocks;

			}
			catch(Exception e)
			{
				logger.info("Sorting on the basis of Market Cap could not be done!");
				return null;
			}
		}

		else if(attribute.compareTo("returnOnEquity")==0)
		{
			try
			{
				System.out.println("Sorting on Return On Equity");

				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = StockWrapperServiceImpl.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}

				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getReturnOnEquity).reversed())      
						.collect(Collectors.toList());
				System.out.println("Sorted Stocks");

				for(int i = 0; i < sortedstocks.size(); i++) {   
					System.out.println("\n\t "+sortedstocks.get(i).getCompanySymbol());
				}

				logger.info("Sorted stocks on the basis of Return On Equity");
				return (ArrayList<StockObject>) sortedstocks;
			}
			catch(Exception e)
			{
				logger.info("Sorting on the basis of Return On Equity could not be done!");
				return null;
			}
		}


		else if(attribute.compareTo("peRatio")==0)
		{
			try
			{
				System.out.println("Sorting on PE Ratio");

				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = StockWrapperServiceImpl.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}
				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getPeRatio).reversed())      
						.collect(Collectors.toList());
				System.out.println("Sorted Stocks");
				for(int i = 0; i < sortedstocks.size(); i++) {   
					System.out.println("\n\t "+sortedstocks.get(i).getCompanySymbol());
				}
				logger.info("Sorted stocks on the basis of PE Ratio");
				return (ArrayList<StockObject>) sortedstocks;
			}
			catch(Exception e)
			{
				logger.info("Sorting on the basis of PE Ratio could not be done!");
				return null;
			}
		}

		else if(attribute.compareTo("change")==0)
		{
			try
			{
				System.out.println("Sorting on Change");

				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = StockWrapperServiceImpl.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}
				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getChange).reversed())      
						.collect(Collectors.toList());
				System.out.println("Sorted Stocks");
				for(int i = 0; i < sortedstocks.size(); i++) {   
					System.out.println("\n\t "+sortedstocks.get(i).getCompanySymbol());
				}
				logger.info("Sorted stocks on the basis of Change");
				return (ArrayList<StockObject>) sortedstocks;
			}
			catch(Exception e)
			{
				logger.info("Sorting on the basis of Change could not be done!");
				return null;
			}
		}


		else
			logger.info("Sorting could not be done!");
		return stocksList;

	}
}
