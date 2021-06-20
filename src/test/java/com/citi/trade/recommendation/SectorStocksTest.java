package com.citi.trade.recommendation;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.service.SectorStocksService;

@SpringBootTest
public class SectorStocksTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorstocksService;


	@Test
	public void testShowCompanies() {

		logger.info("");
		ArrayList<SectorStocks> companies=new ArrayList<SectorStocks>();  
		String sector ="FINANCIAL SERVICES";
		companies = (ArrayList<SectorStocks>) sectorstocksService.getCompanyBySector(sector);
		Assertions.assertNotNull(companies);
		//Assertions.assertNull(sectorstocksService.getCompanyBySector(null));

		//Doubt
		//String companySymbol = "SBIN.NS";
		//Assertions.assertTrue(companies.contains(stockDetailsService.findStock(companySymbol)));


	}
	@Test
	public void testShowCompanySymbols() {

		List<String> companySymbols=new ArrayList<String>();  
		String sector = "AUTOMOBILE";
		companySymbols = sectorstocksService.getCompanySymbolBySector(sector);
		Assertions.assertNotNull(companySymbols);
		Assertions.assertTrue(companySymbols.contains("TATAMOTORS.NS"));
		//Assertions.assertNull(sectorstocksService.getCompanySymbolBySector(null));
	}

	@Test
	public void testShowSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.

		//Doubt
		List<SectorAvg> sectorAvggrowth=new ArrayList<SectorAvg>(); 
		sectorAvggrowth =  sectorstocksService.getSectorWiseGrowth();
		Assertions.assertNotNull(sectorAvggrowth);
	}

	@Test
	public void testShowSectors() {
		// Returns Distinct sectors.
		//Doubt
		List<String> sectors = new ArrayList<String>();  
		sectors =  sectorstocksService.getDistinctSectors();
		Assertions.assertNotNull(sectors);
	}
}



