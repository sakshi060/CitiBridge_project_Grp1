//package com.citi.tradeRecommendation;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.citi.tradeRecommendation.model.SectorStocks;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//@WebAppConfiguration
//public abstract class AbstractTest {
//	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
//	
//   protected MockMvc mvc;
//   @Autowired
//   WebApplicationContext webApplicationContext;
//
//   protected void setUp() {
//	   logger.info( null, logger, logger, logger, logger, logger, webApplicationContext, mvc, logger);
//      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//   }
//   protected String mapToJson(Object obj) throws JsonProcessingException {
//      ObjectMapper objectMapper = new ObjectMapper();
//      return objectMapper.writeValueAsString(obj);
//   }
//   protected <T> ArrayList<SectorStocks> mapFromJson(String json, Class<T> clazz)
//      throws JsonParseException, JsonMappingException, IOException {
//      
//      ObjectMapper objectMapper = new ObjectMapper();
//      ArrayList<SectorStocks> myObjects = objectMapper.readValue(json, new TypeReference<ArrayList<SectorStocks>>(){});
//      return myObjects;
//   }
//}