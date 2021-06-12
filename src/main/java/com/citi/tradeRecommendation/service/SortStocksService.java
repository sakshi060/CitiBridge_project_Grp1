package com.citi.tradeRecommendation.service;

import java.util.ArrayList;
import java.util.List;

import com.citi.tradeRecommendation.model.StockObject;

public interface SortStocksService {

	public ArrayList<StockObject> sort(List<String> companySymbols, String attribute);
}
