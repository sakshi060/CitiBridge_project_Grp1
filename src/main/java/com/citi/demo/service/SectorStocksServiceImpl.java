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
		// TODO Auto-generated method stub
		// Returns Companies of sector passed as an argument from the database.
		ArrayList<SectorStocks> sectorCompanies = new ArrayList<SectorStocks>();
		try
		{
			sectorCompanies = sectorStocksRepository.findCompanyBySector(sector);
			logger.info("Companies under Sector : "+sector+" found!");	
		}
		catch(Exception e)
		{
			logger.error("Sector not found!");
		}
		return sectorCompanies;
	}

	@Override
	public List<String> getCompanySymbolBySector(String sector) {
		// TODO Auto-generated method stub
		List<String> sectorCompanies = new ArrayList<String>();
		try
		{
			sectorCompanies = sectorStocksRepository.findCompanySymbolBySector(sector);
			logger.info("Company Symbols under Sector : "+sector+" found!");	
		}
		catch(Exception e)
		{
			logger.error("Sector not found!");
		}
		return sectorCompanies;
	}
	
	@Override
	public String getSectorByCompanySymbol(String companySymbol) {
		// TODO Auto-generated method stub
		String sector = null;
		try
		{
			sector = sectorStocksRepository.findSectorByCompanySymbol(companySymbol);
			logger.info("Company : "+companySymbol+" belongs to Sector :"+sector);
		}
		catch(Exception e)
		{
			logger.error("Sector for company: "+companySymbol+" not found!");
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
								// TODO Auto-generated catch block
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
			logger.info("Sector Wise Avg Growth!");
			
		}
		catch(Exception e)
		{
			logger.info("Could not find Sector Wise Avg Growth!");
		}
		return sectorWiseGrowth;
	}

	@Override
	public List<String> getDistinctSectors() {
		// TODO Auto-generated method stub
		List<String> sectors = new ArrayList<String>();
		try
		{
			sectors = sectorStocksRepository.findDistinctSectors();
			logger.info("Distinct Sectors found!");
		}
		catch(Exception e)
		{
			logger.info("Distinct sectors could not be found!");
		}
		return sectors;
	}

	
}



