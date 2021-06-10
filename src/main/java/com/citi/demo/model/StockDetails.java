package com.citi.demo.model;

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
	
	public String getCompanySymbol() {
		return companySymbol;
	}


	public void setCompanySymbol(String stockSymbol) {
		this.companySymbol = stockSymbol;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public BigDecimal getOpen() {
		return open;
	}


	public void setOpen(BigDecimal open) {
		this.open = open;
	}


	public BigDecimal getClose() {
		return close;
	}


	public void setClose(BigDecimal close) {
		this.close = close;
	}


	public BigDecimal getHigh() {
		return high;
	}


	public void setHigh(BigDecimal high) {
		this.high = high;
	}


	public BigDecimal getLow() {
		return low;
	}


	public void setLow(BigDecimal low) {
		this.low = low;
	}


	public long getVolume() {
		return volume;
	}


	public void setVolume(long volume) {
		this.volume = volume;
	}


	public BigDecimal getChange() {
		return change;
	}


	public void setChange(BigDecimal change) {
		this.change = change;
	}


	public BigDecimal getPeRatio() {
		return peRatio;
	}


	public void setPeRatio(BigDecimal peRatio) {
		this.peRatio = peRatio;
	}


	public BigDecimal getMarketCap() {
		return marketCap;
	}


	public void setMarketCap(BigDecimal marketCap) {
		this.marketCap = marketCap;
	}

	public List<HistoricalQuote> getHistory() {
		return history;
	}


	public void setHistory(List<HistoricalQuote> history) {
		this.history = history;
	}

	@Override
	public String toString() {
		return "StockDetails [stockSymbol=" + companySymbol + ", companyName=" + companyName + ", open=" + open
				+ ", close=" + close + ", high=" + high + ", low=" + low + ", volume=" + volume + ", change=" + change
				+ ", peRatio=" + peRatio + ", marketCap=" + marketCap + ", returnOnEquity=" 
				+ ", history=" + history + "]";
	}
	
	
	

	
}
