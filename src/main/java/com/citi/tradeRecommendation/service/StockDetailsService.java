package com.citi.tradeRecommendation.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.citi.tradeRecommendation.model.StockDetails;
import com.citi.tradeRecommendation.model.StockObject;

public interface StockDetailsService {

	public StockObject findStock(String companySymbol) throws IOException;
	public StockDetails getStocksDetails(String companySymbol);
	public StockDetails findTopPerformingStock(List<String> companySymbols);
	public  ArrayList<StockDetails> findStocksAndSort(String sector, String attribute);
	public  ArrayList<StockObject> findStocksOnMarketCap(List<String> companySymbols);
	public  ArrayList<StockObject> findStocksOnPeRatio(List<String> companySymbols);
	public  ArrayList<StockObject> findStocksOnChange(List<String> companySymbols);
}
