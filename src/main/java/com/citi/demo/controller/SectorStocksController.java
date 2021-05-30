package com.citi.demo.controller;

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

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.SectorAvg;
import com.citi.demo.model.SectorStocks;
import com.citi.demo.service.SectorStocksService;

@RequestMapping("/sectorStocks")
@RestController
@CrossOrigin(origins="http://localhost:4200")
public class SectorStocksController {
	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksService dao1;
	
	@RequestMapping(value = "/showsCompanies/{sector}", method = RequestMethod.GET)
	public ArrayList<SectorStocks> showShares(@PathVariable String sector) {
		ArrayList<SectorStocks> companies=new ArrayList<SectorStocks>();  

		try
		{
			logger.info("Showing Companies of Sector - " +sector);
			companies = (ArrayList<SectorStocks>) dao1.findCompanyBySector(sector);
			return companies;

		}
		catch(Exception e)
		{
			logger.info("Sector not found!");
			return null;
		}

	}

	@RequestMapping(value = "/showsCompanySymbol/{sector}", method = RequestMethod.GET)
	public List<String> showCompanySymbols(@PathVariable String sector) {
		List<String> companySymbols=new ArrayList<String>();  
		try
		{
			logger.info("Showing Companies of Sector - " +sector);
			companySymbols =  dao1.findCompanySymbolBySector(sector);
			return companySymbols;

		}
		catch(Exception e)
		{
			logger.info("Sector not found!");
			return null;
		}

	}
	
	@RequestMapping(value = "/showSectorWiseChange", method = RequestMethod.GET)
	public List<SectorAvg> showSectorWiseChange() {
		List<SectorAvg> sectors=new ArrayList<SectorAvg>(); 
		try
		{
			logger.info("Showing Sector Wise Growth.");
			sectors =  dao1.getSectorWiseGrowth();
			return sectors;
		}
		catch(Exception e)
		{
			logger.info("Sector Wise data not found!");
			return null;
		}
	}
	
	@RequestMapping(value = "/showDistinctSectors", method = RequestMethod.GET)
	public List<String> showSectors() {
		List<String> sectors = new ArrayList<String>();  
		try
		{
			sectors =  dao1.getDistinctSectors();
			System.out.println(sectors);
			return sectors;

		}
		catch(Exception e)
		{
			logger.info("Sector not found!");
			return null;
		}

	}


	}
	



