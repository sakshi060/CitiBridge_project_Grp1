package com.citi.trade.recommendation.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.repository.SectorStocksRepository;
import com.citi.trade.recommendation.util.SortingParameterList;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class SortStocksServiceTest {
	private static final Logger logger = LogManager.getLogger(SortStocksServiceTest.class);

	@Autowired
	StockDetailsService stockDetailsService;
	@Autowired
	SectorStocksRepository sectorStocksRepository;
	public SectorStocks sectorStocks = new SectorStocks();

	@BeforeEach
	void setUp() {
		String sector = "AUTOMOBILE";
		sectorStocks.setCompanySymbol("TATAMOTORS.NS");
		sectorStocks.setCompanyName("Tata Motors Ltd.");
		sectorStocks.setSector(sector);
	}

	@Test
	@Order(1)
	void addStock() {
		logger.info("Testing setUp for Sort Stocks");
		Assertions.assertTrue(sectorStocksRepository.addSectorStocks(sectorStocks));
	}

	@Test
	@Order(2)
	void testSortOnMarketCap() {
		logger.info("Testing Sort on Market Cap");
		String parameter = SortingParameterList.MARKET_CAP.toString();
		String sector = "AUTOMOBILE";
		List<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(stockDetails);
	}

	@Test
	@Order(3)
	void testSortOnPERatio() {
		logger.info("Testing Sort on PE Ration");
		String parameter = SortingParameterList.PE_RATIO.toString();
		String sector = "AUTOMOBILE";
		List<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(stockDetails);
	}

	@Test
	@Order(4)
	void testSortOnChange() {
		logger.info("Testing Sort on Change");
		String parameter = SortingParameterList.CHANGE.toString();
		String sector = "AUTOMOBILE";
		List<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
		Assertions.assertNotNull(stockDetails);
	}

	@Test
	@Order(5)
	void testDeleteSectors() {
		// Returns Distinct sectors.
		logger.info("Testing Delete for Sort Stocks");
		String sector = "AUTOMOBILE";
		int deleted = sectorStocksRepository.deleteStocksBySector(sector);
		Assertions.assertEquals(1, deleted);
	}

}
