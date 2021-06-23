package com.citi.trade.recommendation.service;

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.util.SortingParameterList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
 class SortStocksTest {
    private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

    @Autowired
    StockDetailsService stockDetailsService;

    @Test
     void testSortOnMarketCap() {
        logger.info("");
        String parameter = SortingParameterList.MARKET_CAP.toString();
        String sector = "AUTOMOBILE";
        List<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
        Assertions.assertNotNull(stockDetails);
        //Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
    }

    @Test
     void testSortOnPERatio() {
        String parameter = SortingParameterList.PE_RATIO.toString();
        String sector = "ENERGY";
        List<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
        Assertions.assertNotNull(stockDetails);
        //Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
    }

    @Test
     void testSortOnChange() {
        String parameter = SortingParameterList.CHANGE.toString();
        String sector = "FINANCIAL SERVICES";
        List<StockDetails> stockDetails = stockDetailsService.findStocksAndSort(sector, parameter);
        Assertions.assertNotNull(stockDetails);
        //Assertions.assertNull(stockDetailsService.findStocksAndSort(null,null));
    }

}



