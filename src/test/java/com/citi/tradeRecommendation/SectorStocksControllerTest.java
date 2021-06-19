//package com.citi.tradeRecommendation;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.ArrayList;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.aspectj.lang.annotation.Before;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.citi.tradeRecommendation.BackendappApplication;
//import com.citi.tradeRecommendation.model.SectorStocks;
//
//@SpringBootTest
//public class SectorStocksControllerTest extends AbstractTest {
//	
//private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
//
//
//@Override
//@Before(value = "")
//public void setUp() {
//   super.setUp();
//}
// 
//   @Test
//   public void getStocksBySector() throws Exception {
//	   
//	  logger.info("Calling Sector Stocks Controller");
//	  String uri = "sectorStocks/showCompanies/FINANCIAL SERVICES";
//      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
//         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//      
//      int status = mvcResult.getResponse().getStatus();
//     
//      assertEquals(200, status);
//      logger.info("Called Sector Stocks Controller");
//      String content = mvcResult.getResponse().getContentAsString();
//      ArrayList<SectorStocks> sectorStocks = super.mapFromJson(content,  SectorStocks.class);
//      assertTrue(sectorStocks.size() > 0);
//      logger.info(" Sector Stocks ");
//   }
//}