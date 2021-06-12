package com.citi.tradeRecommendation.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import yahoofinance.histquotes.HistoricalQuote;

@Data
@NoArgsConstructor
public class StockDetails {
	
	private String companySymbol;
	private String companyName;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private  long volume;
	private  BigDecimal change;
	private  BigDecimal peRatio;
	private  BigDecimal marketCap;
	private List<HistoricalQuote> history;
	
}
