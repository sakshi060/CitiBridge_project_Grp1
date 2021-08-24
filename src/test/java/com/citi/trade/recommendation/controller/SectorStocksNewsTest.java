package com.citi.trade.recommendation.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import com.citi.trade.recommendation.model.Articles;
import com.citi.trade.recommendation.model.NewsData;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SectorStocksController.class })
@WebMvcTest
@ActiveProfiles("test")
class SectorStocksNewsTest {

	private static final Logger logger = LogManager.getLogger(SectorStocksNewsTest.class);
	private MockMvc mockMvc;

	@MockBean
	SectorStocksController sectorStocksController;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void getNews() {
		NewsData newsData = new NewsData();
		newsData.setStatus("OK");
		newsData.setTotalResults(1);
		Articles articles[] = new Articles[1];
		Articles article = new Articles();
		article.setTitle("Testing Automation");
		articles[0] = article;

		String expectedResult = "Testing Automation";
		Mockito.when(sectorStocksController.showNews()).thenReturn(articles);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sectorStocks/getNews");
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