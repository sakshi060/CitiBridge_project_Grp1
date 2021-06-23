package com.citi.trade.recommendation;
import com.citi.trade.recommendation.controller.UserHistoryController;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserHistoryController.class})
@WebMvcTest
 class UserHistoryControllerTest {

    private static final Logger logger = LogManager.getLogger(UserHistoryControllerTest.class);
    private MockMvc mockMvc;

    @MockBean
    StockDetailsService stockDetailsService;
    @MockBean
    UserHistoryService userHistoryService;
    
    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
 
    @Test
     void saveUserHistory()  {
        String expectedResult = "XYZ";
        BigDecimal price = new BigDecimal("539.05");
        UserHistory userHistory = new UserHistory(68,"WIPRO.NS","IT",price,"XYZ",6);
        
        Mockito.when(userHistoryService.saveUserHistoryByuserId(userHistory)).thenReturn(false);

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
            logger.error("Error in saveUserHistoryTest ");
        }
    }
    
    @Test
     void deleteUserHistory()  {
        Integer mockResult = 0;
        int[] id = {1,2};
        
        Mockito.when(userHistoryService.deleteUserHistoryByuserId((int[]) ArgumentMatchers.any())).thenReturn(mockResult);

        try {
        	
            MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/userHistory/deleteSavedStocksByUserId")
                    .content(asJsonString(id))
                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"));
            logger.info("Result is {}", result.getResponse().getContentAsString());
            Assertions.assertEquals( 1, result.getResponse().getContentLength());
            Assertions.assertEquals(200, result.getResponse().getStatus());
        } catch (Exception e) {
            logger.error("Error in deleteUserHistoryTest ");
        }
    }
    
    
     static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Test
     void getUserHistory()  {
        String expectedResult = "Sakshi";
        List<UserHistory> mockResult = new ArrayList<>();
        BigDecimal price = new BigDecimal("539.05");
        UserHistory userHistory = new UserHistory(7,"WIPRO.NS","IT",price,"Sakshi",6);
        mockResult.add(userHistory);
        Mockito.when(userHistoryService.getUserHistoryByuserId(ArgumentMatchers.anyString())).thenReturn(mockResult);
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
     void getCompanySymbolsSavedByUserId()  {
        String expectedResult = "WIPRO.NS";
        List<String> mockResult  = new ArrayList<>();
        mockResult.add("WIPRO.NS");
        
        Mockito.when(userHistoryService.getCompanySymbolsSavedByUserId(ArgumentMatchers.anyString())).thenReturn(mockResult);
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
     void getTopPerformingStock()  {
        String expectedResult = "TCS.NS";
        
        StockDetails stockDetails = new StockDetails("TCS.NS","Tata Consultancy Services Limited",null,null,null,null,0,null,null,null,null);
        Mockito.when(stockDetailsService.findTopPerformingStock(ArgumentMatchers.anyString())).thenReturn(stockDetails);
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
