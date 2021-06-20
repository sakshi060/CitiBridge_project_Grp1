package com.citi.trade.recommendation.controller;

import com.citi.trade.recommendation.model.StockDetails;
import com.citi.trade.recommendation.model.UserHistory;
import com.citi.trade.recommendation.service.SectorStocksService;
import com.citi.trade.recommendation.service.StockDetailsService;
import com.citi.trade.recommendation.service.UserHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RequestMapping("/userHistory")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserHistoryController {
    private static final Logger logger = LogManager.getLogger(UserHistoryController.class);

    @Autowired
    UserHistoryService userHistoryService;
    @Autowired
    SectorStocksService sectorStocksService;
    @Autowired
    StockDetailsService stockDetailsService;

    @PostMapping("/saveStocks")
    public boolean saveUserHistory(@RequestBody UserHistory history) {
        // Saves stock and quantity of the stock, the given user wants.
        return userHistoryService.saveUserHistoryByuserId(history);
    }

    @GetMapping(value = "/showStocks/{userId}")
    public List<UserHistory> getUserHistory(@PathVariable String userId) {
        //Returns saved stocks of userId passed as an argument.
        return userHistoryService.getUserHistoryByuserId(userId);

    }

    @GetMapping(value = "/showTopPerformingStock/{userId}")
    public StockDetails getTopPerformingStock(@PathVariable String userId){
        //Returns Top Performing Stock from Saved Stocks of userId passed as an argument.
        return stockDetailsService.findTopPerformingStock(userId);

    }

    @GetMapping(value = "/getCompanySymbols/{userId}")
    public List<String> getCompanySymbolsSavedByUserId(@PathVariable String userId) {
        //Returns Company Symbols of Saved Stocks of userId passed as an argument.
        return userHistoryService.getCompanySymbolsSavedByUserId(userId);


    }

    @PostMapping(value = "/deleteSavedStocksByUserId")
    public boolean deleteSavedStocksByUserId(@RequestBody int[] ids) {
        // Deletes stocks for the logged in user with ids as parameter.

        int deleted = userHistoryService.deleteUserHistoryByuserId(ids);
        return deleted == 1;

    }


}
