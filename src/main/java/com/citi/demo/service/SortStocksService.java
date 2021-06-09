package com.citi.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.citi.demo.model.StockObject;

public interface SortStocksService {

	public ArrayList<StockObject> sort(List<String> companySymbols, String attribute);
}
