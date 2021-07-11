package com.citi.trade.recommendation.service;

import java.util.List;

import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;

public interface SectorStocksService {

	List<SectorStocks> getCompanyBySector(String sector);

	List<String> getCompanySymbolBySector(String sector);

	List<SectorAvg> getSectorWiseGrowth();

	List<String> getDistinctSectors();
}
