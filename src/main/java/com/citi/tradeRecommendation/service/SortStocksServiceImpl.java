package com.citi.tradeRecommendation.service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.StockObject;
import com.citi.tradeRecommendation.util.SortingParameterList;


@Service
public class SortStocksServiceImpl implements SortStocksService {

	@Autowired
	StockDetailsService stockDetailsService;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	public  ArrayList<StockObject> sort(List<String> companySymbols, String attribute) {
		//Sorts stocks of Companies (companySymbols) on the basis of attribute passed as arguments in the Descending Order.

		ArrayList<StockObject> stocksList=new ArrayList<StockObject>();
		if(attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)
		{
			try
			{
				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = stockDetailsService.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}

				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getMarketCap).reversed())      
						.collect(Collectors.toList());
				return (ArrayList<StockObject>) sortedstocks;

			}
			catch(Exception e)
			{
				return stocksList;
			}
		}

		else if(attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0)
		{
			try
			{
				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = stockDetailsService.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}
				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getPeRatio).reversed())      
						.collect(Collectors.toList());
				return (ArrayList<StockObject>) sortedstocks;
			}
			catch(Exception e)
			{
				return stocksList;
			}
		}

		else if(attribute.compareTo(SortingParameterList.CHANGE.toString())==0)
		{
			try
			{
				for(int i=0;i<companySymbols.size();i++)
				{
					StockObject stock  = stockDetailsService.findStock(companySymbols.get(i));
					stocksList.add(stock);
				}
				List<StockObject> sortedstocks = stocksList.stream()   
						.sorted(Comparator.comparing(StockObject::getChange).reversed())      
						.collect(Collectors.toList());
				return (ArrayList<StockObject>) sortedstocks;
			}
			catch(Exception e)
			{
				return stocksList;
			}
		}


		else
			logger.info("Sorting could not be done!");
		return stocksList;

	}
}