package com.citi.demo;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.citi.demo.model.StockDetails;
import com.citi.demo.service.StockWrapperServiceImpl;

@SpringBootTest
public class StocksServiceTest {

 @Autowired
 StockWrapperServiceImpl stockService;
	@Test
	void invoke() throws IOException
	{
		final StockDetails stockDetails =  stockService.getStocksDetails("BPCL.NS");
		System.out.println(stockDetails);	
	}

	@Test
	public void sortOnMarketCap() throws IOException
	{
		List<String> companySymbols=new ArrayList<String>();  
		 companySymbols.add("JSWSTEEL.NS");  
		 companySymbols.add("ONGC.NS");  
		 companySymbols.add("INFY.NS");  
		 companySymbols.add("DIVISLAB.NS");  
		 StockWrapperServiceImpl.findStocksMarketCap(companySymbols); 
	}
	
	
	@Test
	public void sortOnReturnOnEquity() throws IOException
	{
		List<String> companySymbols=new ArrayList<String>();  
		 companySymbols.add("MARUTI.NS");  
		 companySymbols.add("KOTAKBANK.NS");  
		 companySymbols.add("CIPLA.NS");  
		 companySymbols.add("TCS.NS");  
		 StockWrapperServiceImpl.findStocksReturnOnEquity(companySymbols); 
	}
	
	@Test
	public void sortOnPERatio() throws IOException
	{
		List<String> companySymbols=new ArrayList<String>();  
		
		 companySymbols.add("MARUTI.NS");  
		 companySymbols.add("KOTAKBANK.NS");  
		 companySymbols.add("CIPLA.NS");  
		 companySymbols.add("TCS.NS");  
		 StockWrapperServiceImpl.findStocksPeRatio(companySymbols);
	
	}


}
