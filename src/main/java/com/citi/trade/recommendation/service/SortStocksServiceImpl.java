package com.citi.trade.recommendation.service;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.StockObject;
import com.citi.trade.recommendation.util.SortingParameterList;

@Service
public class SortStocksServiceImpl implements SortStocksService {

	@Autowired
	StockDetailsService stockDetailsService;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	public  List<StockObject> sort(List<String> companySymbols, String attribute) {
		//Sorts stocks of Companies (companySymbols) on the basis of attribute passed as arguments in the Descending Order.
		List<StockObject> sortedStocks = new ArrayList<>();
		try {

			List<StockObject> sectorStocks = stockDetailsService.findAllStock(companySymbols);

			if(attribute.compareTo(SortingParameterList.MARKET_CAP.toString())==0)
			{

				sortedStocks =  sectorStocks.stream()
						.sorted(Comparator.comparing(StockObject::getMarketCap).reversed())      
						.collect(Collectors.toList());
			}

			else if(attribute.compareTo(SortingParameterList.PE_RATIO.toString())==0)
			{
				sortedStocks = sectorStocks.stream()
						.sorted(Comparator.comparing(StockObject::getPeRatio).reversed())      
						.collect(Collectors.toList());
			}

			else if(attribute.compareTo(SortingParameterList.CHANGE.toString())==0)
			{
				sortedStocks = sectorStocks.stream()
						.sorted(Comparator.comparing(StockObject::getChange).reversed())      
						.collect(Collectors.toList());
			}
		}
		catch(Exception e)
		{
			logger.error("Sorting could not be done!");

		}

		return sortedStocks;
	}

}
