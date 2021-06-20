package com.citi.trade.recommendation.service;

import java.util.List;

import com.citi.trade.recommendation.model.StockObject;

public interface SortStocksService {

	public List<StockObject> sort(List<String> companySymbols, String attribute);
}
