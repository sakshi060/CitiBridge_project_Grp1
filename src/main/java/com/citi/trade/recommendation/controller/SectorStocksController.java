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
	return sectorstocksService.getCompanyBySector(sector);


	}

	@RequestMapping(value = "/showCompanySymbol/{sector}", method = RequestMethod.GET)
	public List<String> showCompanySymbols(@PathVariable String sector) {
		// Returns Company Symbols when sector is passed as an argument.
			return sectorstocksService.getCompanySymbolBySector(sector);
	}

	@RequestMapping(value = "/showSectorWiseChange", method = RequestMethod.GET)
	public List<SectorAvg> showSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.

		return sectorstocksService.getSectorWiseGrowth();
	}

	@RequestMapping(value = "/showDistinctSectors", method = RequestMethod.GET)
	public List<String> showDistinctSectors() {
		// Returns Distinct sectors.
 return sectorstocksService.getDistinctSectors();

	}

}




