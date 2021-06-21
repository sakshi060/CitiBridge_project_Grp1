package com.citi.trade.recommendation;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;
import com.citi.trade.recommendation.service.SectorStocksService;
import com.citi.trade.recommendation.service.StockDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class StockDetailsTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorstocksService;
	@Autowired
	StockDetailsService stockDetailsService;

	@Test
	public void testShowRecommendedStocks() {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		logger.info("");
		List<StockDetails> finalList=new ArrayList<StockDetails>();
		String sector = "FINANCIAL SERVICES";
		String parameter = "MARKET_CAP";
		finalList = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(finalList);
		//Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
	}

	@Test
	public void testShowStockDetails() {
		// Returns Stock Details of companySymbol passed as an argument.

		StockDetails stockDetails = new StockDetails();
		String companySymbol = "RELIANCE.NS";
		stockDetails = stockDetailsService.getStocksDetails(companySymbol);
		Assertions.assertNotNull(stockDetails);
		//Assertions.assertNull(stockDetailsService.getStocksDetails(null));
	}

	@Test
	public void getHistory() throws IOException {
		// Returns History of companySymbol passed as an argument.

		StockObject stock = new StockObject();
		String companySymbol = "BPCL.NS";
		stock = stockDetailsService.findStock(companySymbol);
		Assertions.assertNotNull(stock.getHistory());
		//Assertions.assertNull(stockDetailsService.findStock(null).getHistory());
	}

	@Test
	public void getTopPerformingStock()
	{
		String userId = "Sakshi";
		StockDetails stockDetails = stockDetailsService.findTopPerformingStock(userId);
		Assertions.assertNotNull(stockDetails);
		//Assertions.assertNull(stockDetailsService.findTopPerformingStock(null));
	}


}


