package com.citi.trade.recommendation.service;

import java.io.IOException;
import java.util.List;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;


public interface StockDetailsService {

	public StockObject findStock(String companySymbol) throws IOException;
	public List<StockObject> findAllStock(List<String> companySymbol) throws IOException;
	public StockDetails getStocksDetails(String companySymbol);
	public StockDetails findTopPerformingStock(String userId);
	public List<StockDetails> findStocksAndSort(String sector, String attribute);
}
