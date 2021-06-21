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

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.SectorStocksService;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;

import yahoofinance.histquotes.HistoricalQuote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class UserHistoryTest {

<<<<<<< HEAD
	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	StockDetailsService stockDetailsService;
	@Autowired
	SectorStocksService sectorStocksService;

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Test
	public void testsaveUserHistoryByuserId() {
	
	String sector = sectorStocksService.getSectorByCompanySymbol("TCS.NS");
	UserHistory userHistory = new UserHistory();
	userHistory.setUserId("XYZ");
	userHistory.setCompanySymbol("TCS.NS");
	userHistory.setId(100);
	userHistory.setSector(sector);
	try {
		userHistory.setPrice(stockDetailsService.findStock("TCS.NS").getPrice());
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	userHistory.setVolume(45);

	logger.info("");
	Assertions.assertNotNull(userHistoryService.saveUserHistoryByuserId(userHistory));
	//Assertions.assertEquals("TCS.NS",stock.getCompanySymbol());
}
	
	@Test
	public void testdeleteStocksByUserId() {

		String userId = "XYZ";

		int deleted = userHistoryService.deleteUserHistoryByuserId(userId);
		Assertions.assertEquals(1,  deleted);
		//Assertions.assertNull(userHistoryService.getUserHistoryByuserId(null));
	}

	@Test
	public void testgetUserHistoryByuserId()
	{
		String userId = "Rhythm";

		ArrayList<UserHistory> userHistory = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
		Assertions.assertNotNull(userHistory);
		//Assertions.assertNull(userHistoryService.getUserHistoryByuserId(null));
	}

	@Test
	public void testshowTopPerformingStock() throws IOException 
	{
		String userId = "Rhythm";
		StockDetails topStock = new StockDetails();
		List<String> companySymbols=new ArrayList<String>(); 
		companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
		topStock = stockDetailsService.getStocksDetails(companySymbols.get(0));
		Assertions.assertNotNull(topStock);
		//Assertions.assertNull(stockDetailsService.getStocksDetails(null));
	}

	@Test
	public void getCompanySymbolsByUserId() {
		//Returns Company Symbols of Saved Stocks of userId passed as an argument.
		String userId = "Sakshi";
		List<String> companySymbols=new ArrayList<String>();

		companySymbols =  userHistoryService.getCompanySymbolsSavedByUserId(userId);
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		for (int i = 0; i < companySymbols.size(); i++) {
			hm.put(companySymbols.get(i), i);
		}
		Assertions.assertNotNull(companySymbols);
		//Assertions.assertNull(userHistoryService.getCompanySymbolsSavedByUserId(null));

	}


//	@Test
//	public void testdeleteStocks() {
//		int ids[] = {6,7};
//		int deleted = userHistoryService.deleteUserHistoryByuserId(ids);
//			//Assertions.assertNull(userHistoryService.deleteUserHistoryByuserId(null));
//		Assertions.assertEquals(1,  deleted);
//
//	}

	@Test
	public void testgetHistoricalData() throws IOException{
		List<HistoricalQuote> list;
		String companySymbol = "TCS.NS";
		list = stockDetailsService.findStock(companySymbol).getHistory();
		Assertions.assertNotNull(list);
		//Assertions.assertNull(stockDetailsService.findStock(null).getHistory());

	}
=======
    private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
    @Autowired
    UserHistoryService userHistoryService;
    @Autowired
    StockDetailsService stockDetailsService;

    @Test
    public void testsaveUserHistoryByuserId() {
        // TODO Auto-generated method stub
        String userId = "XYZ";
        String companySymbol = "TCS.NS";
        long quantity = 30;
        UserHistory stock = new UserHistory();
        logger.info("");
//        stock = userHistoryService.saveUserHistoryByuserId(userId, companySymbol, quantity);
//        Assertions.assertNotNull(stock);
        //Assertions.assertEquals("TCS.NS",stock.getCompanySymbol());
    }

    @Test
    public void testdeleteStocksByUserId() {

        String userId = "XYZ";

        int deleted = userHistoryService.deleteUserHistoryByuserId(userId);
        Assertions.assertEquals(1, deleted);
        //Assertions.assertNull(userHistoryService.getUserHistoryByuserId(null));
    }

    @Test
    public void testgetUserHistoryByuserId() {
        String userId = "Rhythm";

        ArrayList<UserHistory> userHistory = (ArrayList<UserHistory>) userHistoryService.getUserHistoryByuserId(userId);
        Assertions.assertNotNull(userHistory);
        //Assertions.assertNull(userHistoryService.getUserHistoryByuserId(null));
    }

    @Test
    public void testshowTopPerformingStock() throws IOException {
        String userId = "Rhythm";
        StockDetails topStock = new StockDetails();
        List<String> companySymbols = new ArrayList<String>();
        companySymbols = userHistoryService.getCompanySymbolsSavedByUserId(userId);
        topStock = stockDetailsService.getStocksDetails(companySymbols.get(0));
        Assertions.assertNotNull(topStock);
        //Assertions.assertNull(stockDetailsService.getStocksDetails(null));
    }

    @Test
    public void getCompanySymbolsByUserId() {
        //Returns Company Symbols of Saved Stocks of userId passed as an argument.
        String userId = "Sakshi";
        List<String> companySymbols = new ArrayList<String>();

        companySymbols = userHistoryService.getCompanySymbolsSavedByUserId(userId);
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        for (int i = 0; i < companySymbols.size(); i++) {
            hm.put(companySymbols.get(i), i);
        }
        Assertions.assertNotNull(companySymbols);
        //Assertions.assertNull(userHistoryService.getCompanySymbolsSavedByUserId(null));

    }


    @Test
    public void testdeleteStocks() {
        int ids[] = {2, 5};
        int deleted = userHistoryService.deleteUserHistoryByuserId(ids);
        //Assertions.assertNull(userHistoryService.deleteUserHistoryByuserId(null));
        Assertions.assertEquals(1, deleted);

    }

    @Test
    public void testgetHistoricalData() throws IOException {
        List<HistoricalQuote> list;
        String companySymbol = "TCS.NS";
        list = stockDetailsService.findStock(companySymbol).getHistory();
        Assertions.assertNotNull(list);
        //Assertions.assertNull(stockDetailsService.findStock(null).getHistory());

    }
>>>>>>> ac45cf7753622ac0c652ea5c78b170d64c536d1b
}



