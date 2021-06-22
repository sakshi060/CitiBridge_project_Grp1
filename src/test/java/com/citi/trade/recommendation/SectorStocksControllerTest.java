package com.citi.trade.recommendation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import com.citi.trade.recommendation.model.SectorStocks;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SectorStocksControllerTest {
	
private static final Logger logger = LogManager.getLogger(SectorStocksControllerTest.class);


	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertTrue(this.restTemplate.getForObject("http://localhost:" + port + "/sectorStocks/showDistinctSectors",
				String.class).contains("ENERGY"));
	}

//@BeforeAll
//public void setUp() {
//   
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
// 

}