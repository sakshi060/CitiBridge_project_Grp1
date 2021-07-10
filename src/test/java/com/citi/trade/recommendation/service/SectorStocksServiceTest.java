package com.citi.trade.recommendation.service;

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

import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.repository.SectorStocksRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class SectorStocksServiceTest {
	private static final Logger logger = LogManager.getLogger(SectorStocksServiceTest.class);

	@Autowired
	SectorStocksService sectorstocksService;
	@Autowired
	SectorStocksRepository sectorStocksRepository;

	public SectorStocks sectorStocks = new SectorStocks();

	@Test
	@Order(1)
	public void setUp() {
		logger.info("Testing setUp for Sector Stocks");
		String sector = "FINANCIAL SERVICES";
		sectorStocks.setCompanySymbol("HDFCBANK.NS");
		sectorStocks.setCompanyName("HDFC Bank Ltd.");
		sectorStocks.setSector(sector);
		Assertions.assertTrue(sectorStocksRepository.addSectorStocks(sectorStocks));
	}

	@Test
	@Order(2)
	void testShowCompanies() {
		logger.info("Testing Show Companies");
		String sector = "FINANCIAL SERVICES";
		List<SectorStocks> companySymbols = sectorstocksService.getCompanyBySector(sector);
		Assertions.assertNotNull(companySymbols);
	}

	@Test
	@Order(3)
	void testShowCompanySymbols() {
		logger.info("Testing Show Company Symbols");
		String sector = "FINANCIAL SERVICES";
		List<String> companySymbols = sectorstocksService.getCompanySymbolBySector(sector);
		System.out.println(companySymbols);
		Assertions.assertNotNull(companySymbols);
	}

	@Test
	@Order(4)
	void testShowSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.
		logger.info("Testing Show Sector Wise Change");
		List<SectorAvg> sectorAvggrowth = sectorstocksService.getSectorWiseGrowth();
		Assertions.assertNotNull(sectorAvggrowth);
	}

	@Test
	@Order(5)
	void testShowSectors() {
		// Returns Distinct sectors.
		logger.info("Testing Show Distinct sectors");
		List<String> sectors = sectorstocksService.getDistinctSectors();
		Assertions.assertNotNull(sectors);
	}

	@Test
	@Order(6)
	void testDeleteSectors() {
		// Returns Distinct sectors.
		logger.info("Testing Delete sectors for Sector Stocks");
		String sector = "FINANCIAL SERVICES";
		int deleted = sectorStocksRepository.deleteStocksBySector(sector);
		Assertions.assertEquals(1, deleted);
	}
}
