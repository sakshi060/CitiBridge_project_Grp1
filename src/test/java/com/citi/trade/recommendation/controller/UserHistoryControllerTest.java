package com.citi.trade.recommendation.controller;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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




@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserHistoryController.class})
@WebMvcTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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


	@Order(1)
	@Test
	void saveUserHistory()  {
		String expectedResult = "true";
		BigDecimal price = new BigDecimal("539.05");
		UserHistory userHistory = new UserHistory(1,"WIPRO.NS","IT",price,"XYZ",6);

		Mockito.when(userHistoryService.saveUserHistoryByuserId(userHistory)).thenReturn(true);

		try {
			MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/userHistory/saveStocks")
					.content(asJsonString(userHistory))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

			logger.info("Result is {}", result.getResponse().getContentAsString());
			Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
			Assertions.assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			logger.error("Error in saveUserHistoryTest {} ",e.getMessage() );
		}
	}



	@Order(2)
	@Test
	void getUserHistory()  {
		String expectedResult = "WIPRO.NS";
		List<UserHistory> mockResult = new ArrayList<>();
		BigDecimal price = new BigDecimal("539.05");
		UserHistory userHistory = new UserHistory(1,"WIPRO.NS","IT",price,"XYZ",6);
		mockResult.add(userHistory);
		Mockito.when(userHistoryService.getUserHistoryByuserId(ArgumentMatchers.anyString())).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/userHistory/showStocks/XYZ");

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			logger.info("Result is {}", result.getResponse().getContentAsString());
			Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
			Assertions.assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Order(3)
	@Test
	void getCompanySymbolsSavedByUserId()  {
		String expectedResult = "WIPRO.NS";
		List<String> mockResult  = new ArrayList<>();
		mockResult.add("WIPRO.NS");

		Mockito.when(userHistoryService.getCompanySymbolsSavedByUserId(ArgumentMatchers.anyString())).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/userHistory/getCompanySymbols/XYZ");

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			logger.info("Result is {}", result.getResponse().getContentAsString());
			Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
			Assertions.assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Order(4)
	@Test
	void getTopPerformingStock()  {
		String expectedResult = "WIPRO.NS";

		StockDetails stockDetails = new StockDetails("WIPRO.NS","Wipro Limited",null,null,null,null,0,null,null,null,null);
		Mockito.when(stockDetailsService.findTopPerformingStock(ArgumentMatchers.anyString())).thenReturn(stockDetails);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/userHistory/showTopPerformingStock/XYZ");

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			logger.info("Result is {}", result.getResponse().getContentAsString());
			Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
			Assertions.assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Order(5)
	@Test
	void deleteUserHistory()  {
		Integer mockResult = 1;
		int[] id = {10};

		Mockito.when(userHistoryService.deleteUserHistoryByuserId((int[]) ArgumentMatchers.any())).thenReturn(mockResult);

		try {

			MvcResult result = (MvcResult) mockMvc.perform(MockMvcRequestBuilders.post("/userHistory/deleteSavedStocksByUserId")
					.content(asJsonString(id))
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();

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


}
