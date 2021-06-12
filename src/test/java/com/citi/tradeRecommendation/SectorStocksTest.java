package com.citi.tradeRecommendation;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.tradeRecommendation.model.SectorAvg;
import com.citi.tradeRecommendation.model.SectorStocks;
import com.citi.tradeRecommendation.service.SectorStocksService;

@SpringBootTest
public class SectorStocksTest {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;

	@Test
	public void testShowStocks() {
		
		ArrayList<SectorStocks> companies=new ArrayList<SectorStocks>();  
		String sector ="FINANCIAL SERVICES";
		try
		{
			logger.info("Companies under Sector - " +sector);
			companies = (ArrayList<SectorStocks>) sectorStocksService.getCompanyBySector(sector);
		}
		catch(Exception e)
		{
			logger.info("Sector not found!");
		}
		System.out.println("\n");
		System.out.println("\nCompamies under Sector "+sector+" : ");
		System.out.println(companies);
		System.out.println("\n");

	}
	@Test
	public void testShowCompanySymbols() {
		
		List<String> companySymbols=new ArrayList<String>();  
		String sector = "AUTOMOBILE";
		try
		{
			companySymbols =  sectorStocksService.getCompanySymbolBySector(sector);
			
		}
		catch(Exception e)
		{
			System.out.println("Companies of Sector not found!");
		}
		System.out.println("\n");
		System.out.println("\nCompany Symbols of Stocks under Sector - " +sector);
		System.out.println(companySymbols);
		System.out.println("\n");
	}

	@Test
	public void testShowSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.
		
		List<SectorAvg> sectorAvggrowth=new ArrayList<SectorAvg>(); 
		try
		{
			sectorAvggrowth =  sectorStocksService.getSectorWiseGrowth();
			
		}
		catch(Exception e)
		{
			System.out.println("Sector Wise data not found!");
		}
		System.out.println("\n");
		System.out.println("Sector Wise Growth.");
		System.out.println(sectorAvggrowth);
		System.out.println("\n");
	}
	
	@Test
	public void testShowSectors() {
		// Returns Distinct sectors.
		
		List<String> sectors = new ArrayList<String>();  
		try
		{
			sectors =  sectorStocksService.getDistinctSectors();
			
		}
		catch(Exception e)
		{
			logger.error("Sectors not found!");
		}
		System.out.println("\n");
		System.out.println("Distinct Sectors");
		System.out.println(sectors);
		System.out.println("\n");

	}
}



	