package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.StockObject;

import yahoofinance.histquotes.HistoricalQuote;

import java.util.List;


public interface StockDetailsService {

     StockObject findStock(String companySymbol);

     List<StockObject> findAllStock(List<String> companySymbol);

     StockDetails getStocksDetails(String companySymbol);

     StockDetails findTopPerformingStock(String userId);

     List<StockDetails> findStocksAndSort(String sector, String attribute);
     
     List<HistoricalQuote> findHistory(String companySymbol);
}
