package com.citi.trade.recommendation.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;

@SpringBootTest
@ActiveProfiles("test")
class StockDetailsServiceTest {
	private static final Logger logger = LogManager.getLogger(StockDetailsServiceTest.class);

	@Autowired
	StockDetailsService stockDetailsService;

	@Test
	void testShowRecommendedStocks() {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		logger.info("");
		String sector = "FINANCIAL SERVICES";
		String parameter = "MARKET_CAP";
		List<StockDetails> finalList = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(finalList);
		parameter = "VOLUME";
		Assertions.assertEquals(0, stockDetailsService.findStocksAndSort(sector, parameter).size());
		Assertions.assertEquals(0, stockDetailsService.findStocksAndSort(sector, null).size());

	}

	@Test
	void testShowStockDetails() {
		// Returns Stock Details of companySymbol passed as an argument.

		String companySymbol = "RELIANCE.NS";
		StockDetails stockDetails = stockDetailsService.getStocksDetails(companySymbol);
		Assertions.assertNotNull(stockDetails);
		// Assertions.assertNull(stockDetailsService.getStocksDetails(null));
	}

	@Test
	void getHistory() throws IOException {
		// Returns History of companySymbol passed as an argument.

		String companySymbol = "BPCL.NS";
		StockObject stock = stockDetailsService.findStock(companySymbol);
		Assertions.assertNotNull(stock.getHistory());
		// Assertions.assertNull(stockDetailsService.findStock(null).getHistory());
	}

	@Test
	void getTopPerformingStock() {
		String userId = "Sakshi";
		StockDetails stockDetails = stockDetailsService.findTopPerformingStock(userId);
		Assertions.assertNotNull(stockDetails);
		// Assertions.assertNull(stockDetailsService.findTopPerformingStock(null));
	}

}
