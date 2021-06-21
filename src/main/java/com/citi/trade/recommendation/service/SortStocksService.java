package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.model.StockObject;

import java.util.List;

public interface SortStocksService {

      List<StockObject> sort(List<String> companySymbols, String attribute);
}
