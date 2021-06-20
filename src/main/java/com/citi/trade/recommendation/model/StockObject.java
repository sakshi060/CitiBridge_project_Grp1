package com.citi.trade.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Getter
@With
@AllArgsConstructor
@NoArgsConstructor
public class StockObject // To use Yahoo Finance Functionality 
{
    Stock stock;

    public String getCompanySymbol() {
        return stock.getSymbol();
    }

    public String getCompanyName() {
        return stock.getName();
    }

    public BigDecimal getPrice() {
        return stock.getQuote().getPrice();
    }

    public BigDecimal getOpen() {
        return stock.getQuote().getOpen();
    }

    public BigDecimal getClose() {
        return stock.getQuote().getPreviousClose();
    }

    public BigDecimal getHigh() {
        return stock.getQuote().getDayHigh();
    }

    public BigDecimal getLow() {
        return stock.getQuote().getDayLow();
    }

    public long getVolume() {
        return stock.getQuote().getVolume();
    }

    public BigDecimal getChange() {
        return stock.getQuote().getChange();
    }

    public BigDecimal getMarketCap() {
        return stock.getStats().getMarketCap();
    }

    public BigDecimal getPeRatio() {
        return stock.getStats().getPe();
    }

    public List<HistoricalQuote> getHistory() throws IOException {
        return stock.getHistory();
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
