package com.citi.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.citi.demo.model.SectorAvg;
import com.citi.demo.model.SectorStocks;

@Service
public interface SectorStocksService {
	
	public List<SectorStocks> findCompanyBySector(String sector);
	public List<String> findCompanySymbolBySector(String sector);
	public String findSectorByCompanySymbol(String companySymbol);
	public List<SectorAvg> getSectorWiseGrowth();
	public List<String> getDistinctSectors();
	
	
}
