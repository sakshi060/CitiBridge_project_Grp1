package com.citi.trade.recommendation.controller;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.service.StockDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yahoofinance.histquotes.HistoricalQuote;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/stockDetails")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StockDetailsController {

    private static final Logger logger = LogManager.getLogger(StockDetailsController.class);

    @Autowired
    StockDetailsService stockDetailsService;

    @GetMapping(value = "/showRecommendedStocks/{sector}/{parameter}")
    public List<StockDetails> showRecommendedStocks(@PathVariable String sector, @PathVariable String parameter){
        // Returns sorted stocks of given sector and parameters passed as arguments.
        logger.info("Getting Stocks Recommendation for Sector: {} and Parameter: {}" ,sector,parameter);
        return stockDetailsService.findStocksAndSort(sector, parameter);

    }

    @GetMapping(value = "/showStockDetails/{companySymbol}")
    public StockDetails showStockDetails(@PathVariable String companySymbol) {
        // Returns Stock Details of companySymbol passed as an argument.

		logger.info("Getting Stock Details of Company: {}" ,companySymbol);
        return stockDetailsService.getStocksDetails(companySymbol);

    }

    @GetMapping(value = "/showStockHistory/{companySymbol}")
    public List<HistoricalQuote> getHistory(@PathVariable String companySymbol) {
        // Returns History of companySymbol passed as an argument.
        logger.info("Getting Stock History of Company: {}" ,companySymbol);
		List<HistoricalQuote> history = new ArrayList<>();
		try {
			
			history = stockDetailsService.findHistory(companySymbol);
			
		} catch( Exception e) {
			logger.error("Error in getting history {}", e.getMessage());
		}
		return history;
    }

}
