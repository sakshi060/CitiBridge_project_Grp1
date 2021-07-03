package com.citi.trade.recommendation.controller;

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

import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.service.SectorStocksService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SectorStocksController.class })
@WebMvcTest
@ActiveProfiles("test")
class SectorStocksControllerTest {

	private static final Logger logger = LogManager.getLogger(SectorStocksControllerTest.class);
	private MockMvc mockMvc;

	@MockBean
	SectorStocksService sectorStocksService;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void getCompaniesBySector() {
		String expectedResult = "TATAMOTORS.NS";
		List<SectorStocks> mockResult = new ArrayList<>();
		SectorStocks sectorStocks = new SectorStocks("TATAMOTORS.NS", "Tata Motors Ltd.", "AUTOMOBILE");
		mockResult.add(sectorStocks);
		Mockito.when(sectorStocksService.getCompanyBySector(ArgumentMatchers.anyString())).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sectorStocks/showCompanies/AUTOMOBILE");

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
	void getCompanySymbolsBySector() {
		String expectedResult = "HDFCLIFE.NS";
		List<String> mockResult = new ArrayList<>();
		mockResult.add("HDFCLIFE.NS");
		Mockito.when(sectorStocksService.getCompanySymbolBySector(ArgumentMatchers.anyString())).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/sectorStocks/showCompanySymbol/FINANCIAL SERVICES");

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
	void getDistinctSectors() {
		String expectedResult = "ENERGY";
		List<String> mockResult = new ArrayList<>();
		mockResult.add("ENERGY");
		Mockito.when(sectorStocksService.getDistinctSectors()).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sectorStocks/showDistinctSectors");

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
	void getSectorWiseGrowth() {
		SectorAvg sectorAvg = new SectorAvg();
		sectorAvg.setSector("ENERGY");
		// sectorAvg.setAvgGrowth(-1.7000000000000002);
		String expectedResult = "ENERGY";
		List<SectorAvg> mockResult = new ArrayList<>();
		mockResult.add(sectorAvg);
		Mockito.when(sectorStocksService.getSectorWiseGrowth()).thenReturn(mockResult);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sectorStocks/showSectorWiseChange");

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