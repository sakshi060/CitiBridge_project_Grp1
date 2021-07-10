package com.citi.trade.recommendation.service;

import java.io.IOException;
import java.util.ArrayList;
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
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.repository.SectorStocksRepository;

import yahoofinance.histquotes.HistoricalQuote;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class UserHistoryServiceTest {

	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	StockDetailsService stockDetailsService;
	@Autowired
	SectorStocksRepository sectorStocksRepository;

	public SectorStocks sectorStocks = new SectorStocks();

	private static final Logger logger = LogManager.getLogger(UserHistoryServiceTest.class);

	@Order(1)
	@Test
	void testsaveUserHistoryByuserId() {

		logger.info("Testing setUp for User History");
		String sector = "IT";
		sectorStocks.setCompanySymbol("INFY.NS");
		sectorStocks.setCompanyName("Infosys Ltd.");
		sectorStocks.setSector(sector);
		Assertions.assertTrue(sectorStocksRepository.addSectorStocks(sectorStocks));

		UserHistory userHistoryUser1 = new UserHistory();
		userHistoryUser1.setUserId("XYZ");
		userHistoryUser1.setCompanySymbol("INFY.NS");
		userHistoryUser1.setId(100);
		userHistoryUser1.setSector(sector);

		UserHistory userHistoryUser2 = new UserHistory();
		userHistoryUser2.setUserId("UnknownUser");
		userHistoryUser2.setCompanySymbol("INFY.NS");
		userHistoryUser2.setId(101);
		userHistoryUser2.setSector(sector);

		try {
			userHistoryUser1.setPrice(stockDetailsService.findStock("INFY.NS").getPrice());
			userHistoryUser2.setPrice(stockDetailsService.findStock("INFY.NS").getPrice());
		} catch (Exception e) {

			e.printStackTrace();
		}
		userHistoryUser1.setVolume(45);
		userHistoryUser2.setVolume(46);

		logger.info("");
		System.out.println(userHistoryUser1);
		Assertions.assertTrue(userHistoryService.saveUserHistoryByuserId(userHistoryUser1));
		System.out.println(userHistoryUser2);
		Assertions.assertTrue(userHistoryService.saveUserHistoryByuserId(userHistoryUser2));
	}

	@Order(2)
	@Test
	void testgetUserHistoryByuserId() {
		logger.info("Testing get User History");
		String userId = "XYZ";

		ArrayList<UserHistory> userHistory = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
		Assertions.assertNotNull(userHistory);
	}

	@Order(3)
	@Test
	void testshowTopPerformingStock() {
		String userId = "XYZ";
		logger.info("Testing show Top Performing Stock");
		List<String> companySymbols = userHistoryService.getCompanySymbolsSavedByUserId(userId);
		StockDetails topStock = stockDetailsService.getStocksDetails(companySymbols.get(0));
		Assertions.assertNotNull(topStock);
	}

	@Order(4)
	@Test
	void getCompanySymbolsByUserId() {
		// Returns Company Symbols of Saved Stocks of userId passed as an argument.
		logger.info("Testing get Company Symbol");
		String userId = "XYZ";
		List<String> companySymbols = userHistoryService.getCompanySymbolsSavedByUserId(userId);
		Assertions.assertNotNull(companySymbols);
	}

	@Order(5)
	@Test
	void testgetHistory() throws IOException {
		logger.info("Testing get History");
		List<HistoricalQuote> list;
		String companySymbol = "INFY.NS";
		list = stockDetailsService.findStock(companySymbol).getHistory();
		Assertions.assertNotNull(list);
	}

	@Order(6)
	@Test
	void testdeleteStocks() {
		logger.info("Testing Delete Stocks by Id");
		int ids[] = { 2 };
		int deleted = userHistoryService.deleteUserHistoryByuserId(ids);
		Assertions.assertEquals(1, deleted);
	}

	@Order(7)
	@Test
	void testdeleteStocksByUserId() {
		logger.info("Testing Delete Stocks by userId");
		String userId = "XYZ";
		int deleted = userHistoryService.deleteUserHistoryByuserId(userId);
		Assertions.assertEquals(1, deleted);
	}

	@Test
	@Order(8)
	void testDeleteSectors() {
		// Returns Distinct sectors.
		logger.info("Testing Delete Sectors for User History");
		String sector = "IT";
		int deleted = sectorStocksRepository.deleteStocksBySector(sector);
		Assertions.assertEquals(1, deleted);
	}
}
