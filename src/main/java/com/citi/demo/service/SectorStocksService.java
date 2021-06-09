package com.citi.demo.service;

import java.util.List;

import com.citi.demo.model.SectorAvg;
import com.citi.demo.model.SectorStocks;

public interface SectorStocksService {
	
	public List<SectorStocks> getCompanyBySector(String sector);
	public List<String> getCompanySymbolBySector(String sector);
	public String getSectorByCompanySymbol(String companySymbol);
	public List<SectorAvg> getSectorWiseGrowth();
	public List<String> getDistinctSectors();

}
