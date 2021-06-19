package com.citi.tradeRecommendation.service;


import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.SectorAvg;
import com.citi.tradeRecommendation.model.SectorStocks;
import com.citi.tradeRecommendation.model.StockObject;
import com.citi.tradeRecommendation.repository.SectorStocksRepository;


@Service
public class SectorStocksServiceImpl implements SectorStocksService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	SectorStocksRepository sectorStocksRepository;
	@Autowired
	StockDetailsService stockDetailsService;

	double sum;

	@Override
	public ArrayList<SectorStocks> getCompanyBySector(String sector) {
		// Returns Companies of sector passed as an argument.

		ArrayList<SectorStocks> sectorCompanies = new ArrayList<SectorStocks>();
		try
		{
			sectorCompanies = sectorStocksRepository.findCompanyBySector(sector);
			if(sectorCompanies !=null && sectorCompanies.size()!=0)
				logger.info("Companies under Sector: {} found!",sector);
			else
				logger.error("Sector: {} not found! ",sector);
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
			if(sectorCompanies !=null && sectorCompanies.size()!=0)
				logger.info("Company Symbols under Sector: {} found!",sector);	
			else
				logger.error("Sector: {} not found! ",sector);
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
				logger.info("Company: {} belongs to Sector: {}",companySymbol,sector);
		}
		catch(Exception e)
		{
			logger.error("Sector for company: {} not found! - {}",companySymbol,e.getMessage());
		}
		return sector;
	}

	@Override
	public List<SectorAvg> getSectorWiseGrowth(){
		//Calculates and Returns sector wise growth.

		List<SectorAvg> sectorWiseGrowth = new ArrayList<>();
		try
		{
			logger.info("Finding Sector Wise Avg Growth");
			List<String> sectors = getDistinctSectors();
			if(sectors.size()!=0)
			{
				if(!ObjectUtils.isEmpty(sectors)) {
					sectors.forEach(sector -> {

						List<String> symbols = getCompanySymbolBySector(sector);

						try {
							sum = 0;
							List<StockObject> sectorWiseStocks = stockDetailsService.findAllStock(symbols);
							if(!ObjectUtils.isEmpty(sectorWiseStocks)) {
								sectorWiseStocks.forEach(stock -> {
									if(stock !=null && stock.getChange() != null)
									{sum +=stock.getChange().doubleValue();
									}
								});
							}

						} catch (Exception e) {
							e.printStackTrace();
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
			if(sectors!=null && sectors.size()!=0)
				logger.info("Distinct Sectors found!");
		}
		catch(Exception e)
		{
			logger.error("Distinct sectors could not be found! - {}", e.getMessage());
		}
		return sectors;
	}
}



