package com.citi.trade.recommendation;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.citi.trade.recommendation.controller.StockDetailsController;
import com.citi.trade.recommendation.model.StockDetails;

import com.citi.trade.recommendation.service.StockDetailsService;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {StockDetailsController.class})
@WebMvcTest
public class StockDetailsControllerTest {

    private static final Logger logger = LogManager.getLogger(StockDetailsControllerTest.class);
    private MockMvc mockMvc;

    @MockBean
    private StockDetailsService stockDetailsService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

  
    @Test
    public void getRecommendations()  {
        String expectedResult = "SBILIFE.NS";
        
        BigDecimal open = new BigDecimal(982.0);
        BigDecimal close = new BigDecimal(981.55);
        BigDecimal high = new BigDecimal(1011.0);
        BigDecimal low = new BigDecimal(980.05);
        BigDecimal change = new BigDecimal(25.45);
        BigDecimal peRatio = new BigDecimal(69.20962);
        
        StockDetails stockDetails = new StockDetails();
        stockDetails.setCompanySymbol("SBILIFE.NS");
        stockDetails.setCompanyName("SBI Life Insurance Company Limited");
        stockDetails.setOpen(open);
        stockDetails.setClose(close);
        stockDetails.setHigh(high);
        stockDetails.setLow(low);
        stockDetails.setVolume(2287016);
        stockDetails.setChange(change);
        stockDetails.setPeRatio(peRatio);
        
        List<StockDetails> mockResult = new ArrayList<>();
       
        mockResult.add(stockDetails);
        Mockito.when(stockDetailsService.findStocksAndSort("FINANCIAL SERVICES", "CHANGE")).thenReturn(mockResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stockDetails/showRecommendedStocks/FINANCIAL SERVICES/CHANGE");

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getStockDetails()  {
        String expectedResult = "SBILIFE.NS";
        
        BigDecimal open = new BigDecimal(982.0);
        BigDecimal close = new BigDecimal(981.55);
        BigDecimal high = new BigDecimal(1011.0);
        BigDecimal low = new BigDecimal(980.05);
        BigDecimal change = new BigDecimal(25.45);
        BigDecimal peRatio = new BigDecimal(69.20962);
        StockDetails stockDetails = new StockDetails();
        
        stockDetails.setCompanySymbol("SBILIFE.NS");
        stockDetails.setCompanyName("SBI Life Insurance Company Limited");
        stockDetails.setOpen(open);
        stockDetails.setClose(close);
        stockDetails.setHigh(high);
        stockDetails.setLow(low);
        stockDetails.setVolume(2287016);
        stockDetails.setChange(change);
        stockDetails.setPeRatio(peRatio);
        StockDetails mockResult = stockDetails;
        Mockito.when(stockDetailsService.getStocksDetails(Matchers.anyString())).thenReturn(mockResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stockDetails/showStockDetails/SBILIFE.NS");

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//    @Test
//    public void getStockHistory() throws IOException  {
//        String expectedResult = "SBIN.NS";
//       
//        List<HistoricalQuote> mockResult = new ArrayList<HistoricalQuote>();
//        
//        mockResult = stockDetailsService.findHistory("SBIN.NS");
//        System.out.println(mockResult);
//        Mockito.when(stockDetailsService.findHistory(Matchers.anyString())).thenReturn(mockResult);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stockDetails/showStockHistory/SBIN.NS");
//
//        try {
//            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//            logger.info("Result is {}", result.getResponse().getContentAsString());
//            Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
//            Assertions.assertEquals(200, result.getResponse().getStatus());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
   
}