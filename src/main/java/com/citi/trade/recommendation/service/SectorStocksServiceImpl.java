package com.citi.trade.recommendation.service;


import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.model.StockObject;
import com.citi.trade.recommendation.repository.SectorStocksRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;


@Service
public class SectorStocksServiceImpl implements SectorStocksService {

    private static final Logger logger = LogManager.getLogger(SectorStocksServiceImpl.class);

    @Autowired
    SectorStocksRepository sectorStocksRepository;
    @Autowired
    StockDetailsService stockDetailsService;

    double sum;

    @Override
    public List<SectorStocks> getCompanyBySector(String sector) {
        // Returns Company Names of sector passed as an argument.

        List<SectorStocks> sectorCompanies = new ArrayList<>();
        try {
            sectorCompanies = sectorStocksRepository.findCompanyBySector(sector);
            if (!sectorCompanies.isEmpty())
                logger.info("Companies under Sector: {} found!", sector);
            else
                logger.error("Sector: {} not found! ", sector);
        } catch (Exception e) {
            logger.error("Sector not found! - {}", e.getMessage());
        }
        return sectorCompanies;
    }

    @Override
    public List<String> getCompanySymbolBySector(String sector) {
        // Returns Company Symbols of sector passed as an argument.

        List<String> sectorCompanies = new ArrayList<>();
        try {
            sectorCompanies = sectorStocksRepository.findCompanySymbolBySector(sector);
            if (!sectorCompanies.isEmpty())
                logger.info("Company Symbols under Sector: {} found!", sector);
            else
                logger.error("Sector: {} not found! ", sector);
        } catch (Exception e) {
            logger.error("Sector not found! - {}", e.getMessage());
        }
        return sectorCompanies;
    }

    @Override
    public String getSectorByCompanySymbol(String companySymbol) {
        // Returns  sector of Company Symbol passed as an argument.

        String sector = null;
        try {
            sector = sectorStocksRepository.findSectorByCompanySymbol(companySymbol);
            if (sector != null)
                logger.info("Company: {} belongs to Sector: {}", companySymbol, sector);
        } catch (Exception e) {
            logger.error("Sector for company: {} not found! - {}", companySymbol, e.getMessage());
        }
        return sector;
    }

    @Override
    public List<SectorAvg> getSectorWiseGrowth() {
        //Calculates and Returns sector wise growth.
        List<SectorAvg> sectorWiseGrowth = new ArrayList<>();
        try {
            logger.info("Finding Sector Wise Avg Growth");
            List<String> sectors = getDistinctSectors();
            if (!sectors.isEmpty()) {
                sectors.forEach(sector ->
                        sectorWiseGrowth.add(calculateSectorAvg(sector))
                );
                logger.info("Sector Wise Avg Growth found!");
            }
        } catch (Exception e) {
            logger.error("Could not find Sector Wise Avg Growth! - {}", e.getMessage());
        }
        return sectorWiseGrowth;
    }

    public SectorAvg calculateSectorAvg(String sector) {
        SectorAvg obj = new SectorAvg();
        try {
            List<String> symbols = getCompanySymbolBySector(sector);
            sum = 0;
            List<StockObject> sectorWiseStocks = stockDetailsService.findAllStock(symbols);
            if (!ObjectUtils.isEmpty(sectorWiseStocks)) {
                sectorWiseStocks.forEach(stock -> {
                    if (stock != null && stock.getChange() != null) {
                        sum += stock.getChange().doubleValue();
                    }
                });
            }

            obj.setAvggrowth(sum / symbols.size());
            obj.setSector(sector);
        } catch (Exception e) {
            logger.error("Error in calculating avergae growth {}", e);
        }
        return obj;
    }

    @Override
    public List<String> getDistinctSectors() {
        // Returns Distinct Sectors

        List<String> sectors = new ArrayList<>();
        try {
            sectors = sectorStocksRepository.findDistinctSectors();
            if (!sectors.isEmpty())
                logger.info("Distinct Sectors found!");
        } catch (Exception e) {
            logger.error("Distinct sectors could not be found! - {}", e.getMessage());
        }
        return sectors;
    }
}



