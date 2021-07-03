package com.citi.trade.recommendation.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class SectorStocksServiceTest {
	private static final Logger logger = LogManager.getLogger(SectorStocksServiceTest.class);

	@Autowired
	SectorStocksService sectorstocksService;

	@Test
	void testShowCompanies() {

		String sector = "FINANCIAL SERVICES";
		List<SectorStocks> companies = sectorstocksService.getCompanyBySector(sector);
		Assertions.assertNotNull(companies);
		// Assertions.assertNull(sectorstocksService.getCompanyBySector(null));
		// Doubt
		// String companySymbol = "SBIN.NS";
		// Assertions.assertTrue(companies.contains(stockDetailsService.findStock(companySymbol)));

	}

	@Test
	void testShowCompanySymbols() {

		String sector = "AUTOMOBILE";
		List<String> companySymbols = sectorstocksService.getCompanySymbolBySector(sector);
		Assertions.assertNotNull(companySymbols);
		Assertions.assertTrue(companySymbols.contains("TATAMOTORS.NS"));
		// Assertions.assertNull(sectorstocksService.getCompanySymbolBySector(null));
	}

	@Test
	void testShowSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.

		// Doubt
		List<SectorAvg> sectorAvggrowth = sectorstocksService.getSectorWiseGrowth();
		Assertions.assertNotNull(sectorAvggrowth);
	}

	@Test
	void testShowSectors() {
		// Returns Distinct sectors.
		// Doubt
		List<String> sectors = sectorstocksService.getDistinctSectors();
		Assertions.assertNotNull(sectors);
	}
}
