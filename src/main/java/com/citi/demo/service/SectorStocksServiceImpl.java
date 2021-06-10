package com.citi.demo.service;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.SectorAvg;
import com.citi.demo.model.SectorStocks;
import com.citi.demo.repository.SectorStocksRepository;

@Service
public class SectorStocksServiceImpl implements SectorStocksService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksRepository sectorStocksRepository;
	@Autowired
	StockRecommendationService stockRecommendationService;
	double sum;

	@Override
	public ArrayList<SectorStocks> getCompanyBySector(String sector) {
		// Returns Companies of sector passed as an argument.

		ArrayList<SectorStocks> sectorCompanies = new ArrayList<SectorStocks>();
		try
		{
			sectorCompanies = sectorStocksRepository.findCompanyBySector(sector);
			if(sectorCompanies.size()!=0)
				logger.info("Companies under Sector : "+sector+" found!");	
		}
		catch(Exception e)
		{
			logger.error("Sector not found! - {}",e.getMessage());
		}
		return sectorCompanies;
	}

	@Override
	public List<String> getCompanySymbolBySector(String sector) {
		// Returns Company Symbols of sector passed as an argument.

		List<String> sectorCompanies = new ArrayList<String>();
		try
		{
			sectorCompanies = sectorStocksRepository.findCompanySymbolBySector(sector);
			if(sectorCompanies.size()!=0)
				logger.info("Company Symbols under Sector : "+sector+" found!");	
		}
		catch(Exception e)
		{
			logger.error("Sector not found! - {}",e.getMessage());
		}
		return sectorCompanies;
	}

	@Override
	public String getSectorByCompanySymbol(String companySymbol) {
		// Returns  sector of Company Symbol passed as an argument.
		
		String sector = null;
		try
		{
			sector = sectorStocksRepository.findSectorByCompanySymbol(companySymbol);
			if(sector!=null)
				logger.info("Company : "+companySymbol+" belongs to Sector :"+sector);
		}
		catch(Exception e)
		{
			logger.error("Sector for company: "+companySymbol+" not found! - {}",e.getMessage());
		}
		return sector;
	}

	@Override
	public List<SectorAvg> getSectorWiseGrowth(){
		//Calculates and Returns sector wise growth.
		
		List<SectorAvg> sectorWiseGrowth = new ArrayList<>();
		try
		{
			List<String> sectors = getDistinctSectors();
			if(sectors.size()!=0)
			{
				System.out.println(sectors);

				if(!ObjectUtils.isEmpty(sectors)) {
					sectors.forEach(sector -> {

						List<String> symbols = getCompanySymbolBySector(sector);
						if(!ObjectUtils.isEmpty(symbols)) {
							sum = 0;
							symbols.forEach(symbol -> {
								try {
									sum +=stockRecommendationService.findStock(symbol).getChange().doubleValue();
								} catch (Exception e) {
									e.printStackTrace();
								}	
							});

						}
						SectorAvg obj = new SectorAvg();
						obj.setAvggrowth(sum/symbols.size());
						obj.setSector(sector);
						sectorWiseGrowth.add(obj);

					});

				}
				logger.info("Sector Wise Avg Growth found!");
			}
		}
		catch(Exception e)
		{
			logger.error("Could not find Sector Wise Avg Growth! - {}",e.getMessage());
		}
		return sectorWiseGrowth;
	}

	@Override
	public List<String> getDistinctSectors() {
		// Returns Distinct Sectors
		
		List<String> sectors = new ArrayList<String>();
		try
		{
			sectors = sectorStocksRepository.findDistinctSectors();
			if(sectors.size()!=0)
				logger.info("Distinct Sectors found!");
		}
		catch(Exception e)
		{
			logger.error("Distinct sectors could not be found! - {}", e.getMessage());
		}
		return sectors;
	}
}



