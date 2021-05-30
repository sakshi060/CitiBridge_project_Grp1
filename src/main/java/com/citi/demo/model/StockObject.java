package com.citi.demo.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;
@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class StockObject
{
	public Stock stock;
	private LocalDateTime lastAccessed;




	public StockObject(final Stock stock)
	{
		this.stock = stock;
		this.lastAccessed = LocalDateTime.now();
		
	}
	public StockObject() {
		// TODO Auto-generated constructor stub
	}
	public String getCompanySymbol()
	{
		return stock.getSymbol();
	}
	public String getCompanyName()
	{
		return stock.getName();
	}
	public BigDecimal getPrice()
	{
		return stock.getQuote().getPrice();
	}
	
	public BigDecimal getOpen()
	{
		return stock.getQuote().getOpen();
	}
	public BigDecimal getClose()
	{
		return stock.getQuote().getPreviousClose();
	}
	public BigDecimal getHigh()
	{
		return stock.getQuote().getDayHigh();
	}
	public BigDecimal getLow()
	{
		return stock.getQuote().getDayLow();
	}
	public long getVolume()
	{
		return stock.getQuote().getVolume();
	}
	public BigDecimal getChange()
	{
		return stock.getQuote().getChange();
	}
	public BigDecimal getMarketCap()
	{
		return stock.getStats().getMarketCap();
	}
	public BigDecimal getReturnOnEquity()
	{
		return stock.getStats().getROE();
	}
	public BigDecimal getPeRatio()
	{
		return stock.getStats().getPe();
	}
	public List<HistoricalQuote> getHistory() throws IOException
	{
		return stock.getHistory();
	}
	public Stock getStock()
	{
		return stock;
	}
	
}
