package com.citi.trade.recommendation.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.service.StockDetailsService;

import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { StockDetailsController.class })
@WebMvcTest
@ActiveProfiles("test")
class StockDetailsControllerTest {

	private static final Logger logger = LogManager.getLogger(StockDetailsControllerTest.class);
	MockMvc mockMvc;

	@MockBean
	StockDetailsService stockDetailsService;
	@Autowired
	StockDetailsService stock;

	@Autowired
	WebApplicationContext webApplicationContext;

	BigDecimal open, close, high, low, change, peRatio;
	StockDetails stockDetails = new StockDetails();

	@BeforeEach
	void setUp() {
		open = new BigDecimal("982.0");
		close = new BigDecimal("981.55");
		high = new BigDecimal("1011.0");
		low = new BigDecimal("980.05");
		change = new BigDecimal("25.45");
		peRatio = new BigDecimal("69.20962");
		stockDetails.setCompanySymbol("SBILIFE.NS");
		stockDetails.setCompanyName("SBI Life Insurance Company Limited");
		stockDetails.setOpen(open);
		stockDetails.setClose(close);
		stockDetails.setHigh(high);
		stockDetails.setLow(low);
		stockDetails.setVolume(2287016);
		stockDetails.setChange(change);
		stockDetails.setPeRatio(peRatio);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void getRecommendations() {
		String expectedResult = "SBILIFE.NS";
		List<StockDetails> mockResult = new ArrayList<>();

		mockResult.add(stockDetails);
		Mockito.when(stockDetailsService.findStocksAndSort("FINANCIAL SERVICES", "CHANGE")).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/stockDetails/showRecommendedStocks/FINANCIAL SERVICES/CHANGE");

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
	void getStockDetails() {
		String expectedResult = "SBILIFE.NS";
		StockDetails mockResult = stockDetails;
		Mockito.when(stockDetailsService.getStocksDetails(ArgumentMatchers.anyString())).thenReturn(mockResult);
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

	@Test
	void getStockHistory() throws IOException {
		String expectedResult = "SBILIFE.NS";

		List<HistoricalQuote> mockResult;
		mockResult = history(expectedResult);
		Mockito.when(stockDetailsService.findHistory(ArgumentMatchers.anyString())).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stockDetails/showStockHistory/SBILIFE.NS");

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			logger.info("Result is {}", result.getResponse().getContentAsString());
			Assertions.assertTrue(result.getResponse().getContentAsString().contains(expectedResult));
			Assertions.assertEquals(200, result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	List<HistoricalQuote> history(String expectedResult) throws IOException {
		return YahooFinance.get(expectedResult).getHistory();
	}

}