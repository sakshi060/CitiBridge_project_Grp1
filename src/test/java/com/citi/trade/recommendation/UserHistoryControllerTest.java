package com.citi.trade.recommendation;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.citi.trade.recommendation.controller.UserHistoryController;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserHistoryController.class})
@WebMvcTest
public class UserHistoryControllerTest {

    private static final Logger logger = LogManager.getLogger(UserHistoryControllerTest.class);
    private MockMvc mockMvc;

    @MockBean
    private StockDetailsService stockDetailsService;
    @MockBean
    private UserHistoryService userHistoryService;
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
 
    @Test
    public void saveUserHistory()  {
        String expectedResult = "XYZ";
        boolean mockResult = false;
        BigDecimal price = new BigDecimal(539.05);
        UserHistory userHistory = new UserHistory(68,"WIPRO.NS","IT",price,"XYZ",6);
        
        Mockito.when(userHistoryService.saveUserHistoryByuserId(userHistory)).thenReturn(mockResult);
        //RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userHistory/saveStocks");

        try {
        	System.out.println(asJsonString(userHistory));
            MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/userHistory/saveStocks")
                    .content(asJsonString(userHistory))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            
        }
    }
    
    @Test
    public void deleteUserHistory()  {
//        int expectedResult = 1;
        Integer mockResult = 0;
        int id[] = {1,2};
        
        Mockito.when(userHistoryService.deleteUserHistoryByuserId(id)).thenReturn(mockResult);
        //RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userHistory/saveStocks");

        try {
        	
            MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/userHistory/deleteSavedStocksByUserId")
                    .content(asJsonString(id))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentLength()==1);
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            
        }
    }
    
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
    public void getUserHistory()  {
        String expectedResult = "Sakshi";
        List<UserHistory> mockResult = new ArrayList<>();
        BigDecimal price = new BigDecimal(539.05);
        UserHistory userHistory = new UserHistory(7,"WIPRO.NS","IT",price,"Sakshi",6);
        mockResult.add(userHistory);
        Mockito.when(userHistoryService.getUserHistoryByuserId(Matchers.anyString())).thenReturn(mockResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/userHistory/showStocks/Sakshi");

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
    public void getCompanySymbolsSavedByUserId()  {
        String expectedResult = "WIPRO.NS";
        List<String> mockResult  = new ArrayList<>();
        mockResult.add("WIPRO.NS");
        
        Mockito.when(userHistoryService.getCompanySymbolsSavedByUserId(Matchers.anyString())).thenReturn(mockResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/userHistory/getCompanySymbols/Sakshi");

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
    public void getTopPerformingStock()  {
        String expectedResult = "TCS.NS";
        StockDetails mockResult = new StockDetails();
        
        StockDetails stockDetails = new StockDetails("TCS.NS","Tata Consultancy Services Limited",null,null,null,null,0,null,null,null,null);
        mockResult = stockDetails;
        Mockito.when(stockDetailsService.findTopPerformingStock(Matchers.anyString())).thenReturn(mockResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/userHistory/showTopPerformingStock/Sakshi");

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
