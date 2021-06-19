package com.citi.tradeRecommendation.service;

import java.util.List;

import com.citi.tradeRecommendation.model.StockObject;

public interface SortStocksService {

	public List<StockObject> sort(List<String> companySymbols, String attribute);
}
