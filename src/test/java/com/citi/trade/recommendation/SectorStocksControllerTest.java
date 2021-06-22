package com.citi.trade.recommendation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.citi.trade.recommendation.controller.SectorStocksController;
import com.citi.trade.recommendation.service.SectorStocksService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import com.citi.trade.recommendation.model.SectorStocks;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SectorStocksController.class)
@WebMvcTest
public class SectorStocksControllerTest {

    private static final Logger logger = LogManager.getLogger(SectorStocksControllerTest.class);
    private MockMvc mockMvc;

    @MockBean
    private SectorStocksService sectorStocksService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getDistinctSectors()  {
        String expectedResult = "ENERGY";
        List<String> mockResult = new ArrayList<>();
        mockResult.add("ENERGY");
        Mockito.when(sectorStocksService.getDistinctSectors()).thenReturn(mockResult);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/sectorStocks/showDistinctSectors");

        try {
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            logger.info("Result is {}", result.getResponse());
            assertTrue(result.getResponse().getContentLength() ==1);
        } catch (Exception e) {
            e.printStackTrace();
        }



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