package com.citi.trade.recommendation;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
 class UserHistoryServiceTest {

	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	StockDetailsService stockDetailsService;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Test
	 void testsaveUserHistoryByuserId() {
	
	String sector = "IT";
	UserHistory userHistory = new UserHistory();
	userHistory.setUserId("XYZ");
	userHistory.setCompanySymbol("TCS.NS");
	userHistory.setId(100);
	userHistory.setSector(sector);
	try {
		userHistory.setPrice(stockDetailsService.findStock("TCS.NS").getPrice());
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	userHistory.setVolume(45);

	logger.info("");
	Assertions.assertTrue(userHistoryService.saveUserHistoryByuserId(userHistory));
	//Assertions.assertEquals("TCS.NS",stock.getCompanySymbol());
}
	
	@Test
	 void testdeleteStocksByUserId() {

		String userId = "XYZ";

		int deleted = userHistoryService.deleteUserHistoryByuserId(userId);
		Assertions.assertEquals(1,  deleted);
		//Assertions.assertNull(userHistoryService.getUserHistoryByuserId(null));
	}

	@Test
	 void testgetUserHistoryByuserId()
	{
		String userId = "Rhythm";

		ArrayList<UserHistory> userHistory = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
		Assertions.assertNotNull(userHistory);
		//Assertions.assertNull(userHistoryService.getUserHistoryByuserId(null));
	}

	@Test
	 void testshowTopPerformingStock()
	{
		String userId = "Rhythm";
		List<String> companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
		StockDetails topStock = stockDetailsService.getStocksDetails(companySymbols.get(0));
		Assertions.assertNotNull(topStock);
		//Assertions.assertNull(stockDetailsService.getStocksDetails(null));
	}

	@Test
	 void getCompanySymbolsByUserId() {
		//Returns Company Symbols of Saved Stocks of userId passed as an argument.
		String userId = "Sakshi";
		List<String> companySymbols= userHistoryService.getCompanySymbolsSavedByUserId(userId);
		Assertions.assertNotNull(companySymbols);
		//Assertions.assertNull(userHistoryService.getCompanySymbolsSavedByUserId(null));

	}

//	@Test
//	 void testdeleteStocks() {
//		int ids[] = {6,7};
//		int deleted = userHistoryService.deleteUserHistoryByuserId(ids);
//			//Assertions.assertNull(userHistoryService.deleteUserHistoryByuserId(null));
//		Assertions.assertEquals(1,  deleted);
//
//	}

	@Test
	 void testgetHistoricalData() throws IOException{
		List<HistoricalQuote> list;
		String companySymbol = "TCS.NS";
		list = stockDetailsService.findStock(companySymbol).getHistory();
		Assertions.assertNotNull(list);
		//Assertions.assertNull(stockDetailsService.findStock(null).getHistory());

	}
}



