package com.citi.demo;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.demo.model.StockDetails;
import com.citi.demo.service.SectorStocksService;
import com.citi.demo.service.StockRecommendationService;
import com.citi.demo.util.SortingParameterList;

@SpringBootTest
public class SortStocksTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorstocksService;
	@Autowired
	StockRecommendationService stockRecommendationService;
	
	@Test
	public void testSortOnMarketCap()
	{
		try
		{
			String parameter = SortingParameterList.MARKET_CAP.toString();
			String sector = "ENERGY";
			
			ArrayList<StockDetails> stockDetails = stockRecommendationService.findStocksAndSort(sector,parameter); 
			System.out.println("\n Sorted on the basis of Market Capital");
			System.out.println(stockDetails);
			System.out.println("\n");
		}
		catch(Exception e)
		{
			logger.info("Sorting could not be done!");
		}
	}

	@Test
	public void testSortOnPERatio()
	{
		try
		{
			String parameter = SortingParameterList.PE_RATIO.toString() ;
			String sector = "ENERGY";
			
			ArrayList<StockDetails> stockDetails = stockRecommendationService.findStocksAndSort(sector, parameter);
			System.out.println("\n Sorted on the basis of PE Ratio");
			System.out.println(stockDetails);
			System.out.println("\n");
		}
		catch(Exception e)
		{
			logger.info("Sorting could not be done!");
		}

	}

	@Test
	public void testSortOnChange()
	{
		try
		{
			String parameter = SortingParameterList.CHANGE.toString();
			String sector = "ENERGY";
			
			ArrayList<StockDetails> stockDetails = stockRecommendationService.findStocksAndSort(sector, parameter);
			System.out.println("\n Sorted on the basis of Change");
			System.out.println(stockDetails);
			System.out.println("\n");
		}
		catch(Exception e)
		{
			logger.info("Sorting could not be done!");
		}

	}
}



