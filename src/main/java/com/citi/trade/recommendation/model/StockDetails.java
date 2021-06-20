package com.citi.trade.recommendation.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import yahoofinance.histquotes.HistoricalQuote;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDetails {
	 String companySymbol;
	 String companyName;
	 BigDecimal open;
	 BigDecimal close;
	 BigDecimal high;
	 BigDecimal low;
	  long volume;
	  BigDecimal change;
	  BigDecimal peRatio;
	  BigDecimal marketCap;
	 List<HistoricalQuote> history;
}
