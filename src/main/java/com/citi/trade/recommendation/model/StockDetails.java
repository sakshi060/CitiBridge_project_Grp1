package com.citi.trade.recommendation.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yahoofinance.histquotes.HistoricalQuote;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
