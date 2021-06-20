package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;

import java.util.List;

public interface SectorStocksService {

    public List<SectorStocks> getCompanyBySector(String sector);

    public List<String> getCompanySymbolBySector(String sector);

    public String getSectorByCompanySymbol(String companySymbol);

    public List<SectorAvg> getSectorWiseGrowth();

    public List<String> getDistinctSectors();
}
