package com.citi.tradeRecommendation.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.SectorAvg;
import com.citi.tradeRecommendation.model.SectorStocks;
import com.citi.tradeRecommendation.service.SectorStocksService;

@RequestMapping("/sectorStocks")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class SectorStocksController {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorStocksService;

	@RequestMapping(value = "/showCompanies/{sector}", method = RequestMethod.GET)
	public ArrayList<SectorStocks> showStocks(@PathVariable String sector) {
		// Returns Companies when sector is passed as an argument.

		ArrayList<SectorStocks> companies=new ArrayList<SectorStocks>();  
		try
		{
			companies = (ArrayList<SectorStocks>) sectorStocksService.getCompanyBySector(sector);
			if(companies.size()!=0)
				logger.info("Showing Companies under Sector - " +sector);
		}
		catch(Exception e)
		{
			logger.error("Companies under Sector not found! - {}",e.getMessage());	
		}
		return companies;

	}

	@RequestMapping(value = "/showCompanySymbol/{sector}", method = RequestMethod.GET)
	public List<String> showCompanySymbols(@PathVariable String sector) {
		// Returns Company Symbols when sector is passed as an argument.

		List<String> companySymbols=new ArrayList<String>();  
		try
		{
			companySymbols =  sectorStocksService.getCompanySymbolBySector(sector);
			if(companySymbols.size()!=0)
				logger.info("Showing Company Symbols of Stocks under Sector - " +sector);
		}
		catch(Exception e)
		{
			logger.error("Companies of Sector not found! - {}",e.getMessage());
		}
		return companySymbols;

	}

	@RequestMapping(value = "/showSectorWiseChange", method = RequestMethod.GET)
	public List<SectorAvg> showSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.

		List<SectorAvg> sectors=new ArrayList<SectorAvg>(); 
		try
		{
			sectors =  sectorStocksService.getSectorWiseGrowth();
			if(sectors.size()!=0)
				logger.info("Showing Sector Wise Growth");
		}
		catch(Exception e)
		{
			logger.error("Sector Wise data not found! - {}",e.getMessage());
		}
		return sectors;
	}

	@RequestMapping(value = "/showDistinctSectors", method = RequestMethod.GET)
	public List<String> showSectors() {
		// Returns Distinct sectors.

		List<String> sectors = new ArrayList<String>();  
		try
		{
			sectors =  sectorStocksService.getDistinctSectors();
			if(sectors.size()!=0)
				logger.info("Showing Distinct Sectors");
		}
		catch(Exception e)
		{
			logger.error("Sectors not found! {} - ",e.getMessage());
		}
		return sectors;

	}

}




