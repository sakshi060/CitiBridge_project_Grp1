package com.citi.trade.recommendation.service;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.repository.SectorStocksRepository;
import com.citi.trade.recommendation.util.SortingParameterList;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockDetailsServiceTest {
	private static final Logger logger = LogManager.getLogger(StockDetailsServiceTest.class);

	@Autowired
	StockDetailsService stockDetailsService;
	@Autowired
	SectorStocksRepository sectorStocksRepository;
	@Autowired
	UserHistoryService userHistoryService;

	public SectorStocks sectorStocks = new SectorStocks();

	@Test
	@Order(1)
	public void setUp() {
		logger.info("Testing setUp for Stock Details");
		String sector = "ENERGY";
		sectorStocks.setCompanySymbol("IOC.NS");
		sectorStocks.setCompanyName("Indian Oil Corporation Ltd.");
		sectorStocks.setSector(sector);
		Assertions.assertTrue(sectorStocksRepository.addSectorStocks(sectorStocks));
		UserHistory userHistory = new UserHistory();
		userHistory.setUserId("XYZ");
		userHistory.setCompanySymbol("IOC.NS");
		userHistory.setId(100);
		userHistory.setSector(sector);
		try {
			userHistory.setPrice(stockDetailsService.findStock("IOC.NS").getPrice());
		} catch (Exception e) {

			e.printStackTrace();
		}
		userHistory.setVolume(45);

		logger.info("");
		Assertions.assertTrue(userHistoryService.saveUserHistoryByuserId(userHistory));
	}

	@Test
	@Order(2)
	void testShowRecommendedStocks() {
		// Returns sorted stocks of given sector and parameters passed as arguments.

		logger.info("Testing show Recommended Stocks");
		String sector = "ENERGY";
		String parameter = SortingParameterList.MARKET_CAP.toString();
		List<StockDetails> finalList = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(finalList);
		parameter = "VOLUME";
		Assertions.assertEquals(0, stockDetailsService.findStocksAndSort(sector, parameter).size());
		Assertions.assertEquals(0, stockDetailsService.findStocksAndSort(sector, null).size());

	}

	@Test
	@Order(3)
	void testShowStockDetails() {
		// Returns Stock Details of companySymbol passed as an argument.

		logger.info("Testing show Stock Details");
		String companySymbol = "IOC.NS";
		StockDetails stockDetails = stockDetailsService.getStocksDetails(companySymbol);
		Assertions.assertNotNull(stockDetails);
	}

	@Test
	@Order(4)
	void getHistory() throws IOException {
		// Returns History of companySymbol passed as an argument.

		logger.info("Testing get History");
		String companySymbol = "IOC.NS";
		StockObject stock = stockDetailsService.findStock(companySymbol);
		Assertions.assertNotNull(stock.getHistory());
	}

	@Test
	@Order(5)
	void getTopPerformingStock() {
		logger.info("Testing get Top Performing Stock");
		String userId = "XYZ";
		StockDetails stockDetails = stockDetailsService.findTopPerformingStock(userId);
		Assertions.assertNotNull(stockDetails);
	}

	@Test
	@Order(6)
	void testDelete() {
		// Returns Distinct sectors.
		logger.info("Testing delete for Stock Details");
		String sector = "ENERGY";
		int deletedSector = sectorStocksRepository.deleteStocksBySector(sector);
		Assertions.assertEquals(1, deletedSector);

		String userId = "XYZ";
		int deletedUser = userHistoryService.deleteUserHistoryByuserId(userId);
		Assertions.assertEquals(1, deletedUser);
	}

}
