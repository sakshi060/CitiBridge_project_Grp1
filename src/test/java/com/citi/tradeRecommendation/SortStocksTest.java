package com.citi.tradeRecommendation;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.tradeRecommendation.model.StockDetails;
import com.citi.tradeRecommendation.service.StockDetailsService;
import com.citi.tradeRecommendation.util.SortingParameterList;

@SpringBootTest
public class SortStocksTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	StockDetailsService stockDetailsService;

	@Test
	public void testSortOnMarketCap()
	{
		logger.info("");
		String parameter = SortingParameterList.MARKET_CAP.toString();
		String sector = "AUTOMOBILE";
		ArrayList<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector,parameter); 
		Assertions.assertNotNull(stockDetails);
		//Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
	}

	@Test
	public void testSortOnPERatio()
	{
		String parameter = SortingParameterList.PE_RATIO.toString() ;
		String sector = "ENERGY";
		ArrayList<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(stockDetails);
		//Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
	}

	@Test
	public void testSortOnChange()
	{
		String parameter = SortingParameterList.CHANGE.toString();
		String sector = "FINANCIAL SERVICES";
		ArrayList<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(stockDetails);
		//Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
	}

}



