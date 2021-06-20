package com.citi.trade.recommendation.controller;

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

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.service.SectorStocksService;

@RequestMapping("/sectorStocks")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class SectorStocksController {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService sectorstocksService;

	@RequestMapping(value = "/showCompanies/{sector}", method = RequestMethod.GET)
	public List<SectorStocks> showStocks(@PathVariable String sector) {
		// Returns Companies when sector is passed as an argument.

		ArrayList<SectorStocks> companies=new ArrayList<>();
		try
		{
			companies = (ArrayList<SectorStocks>) sectorstocksService.getCompanyBySector(sector);
			if(companies!= null && companies.size()!=0)
				logger.info("Showing Companies under Sector - {}",sector);
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

		List<String> companySymbols=new ArrayList<>();
		try
		{
			companySymbols =  sectorstocksService.getCompanySymbolBySector(sector);
			if(companySymbols.isEmpty())
				logger.info("Showing Company Symbols of Stocks under Sector - {}" +sector);
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

		List<SectorAvg> sectors=new ArrayList<>();
		try
		{
			sectors =  sectorstocksService.getSectorWiseGrowth();
			if(sectors!=null && sectors.size()!=0)
				logger.info("Showing Sector Wise Growth");
		}
		catch(Exception e)
		{
			logger.error("Sector Wise data not found! - {}",e.getMessage());
		}
		return sectors;
	}

	@RequestMapping(value = "/showDistinctSectors", method = RequestMethod.GET)
	public List<String> showDistinctSectors() {
		// Returns Distinct sectors.

		List<String> sectors = new ArrayList<>();
		try
		{
			sectors =  sectorstocksService.getDistinctSectors();
			if(!sectors.isEmpty())
				logger.info("Showing Distinct Sectors");
		}
		catch(Exception e)
		{
			logger.error("Sectors not found! - {}  ",e.getMessage());
		}
		return sectors;

	}

}




