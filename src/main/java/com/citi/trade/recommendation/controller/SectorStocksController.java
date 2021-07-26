package com.citi.trade.recommendation.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.citi.trade.recommendation.model.Articles;
import com.citi.trade.recommendation.model.NewsData;
import com.citi.trade.recommendation.model.SectorAvg;
import com.citi.trade.recommendation.model.SectorStocks;
import com.citi.trade.recommendation.service.SectorStocksService;

@RequestMapping("/sectorStocks")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SectorStocksController {
	private static final Logger logger = LogManager.getLogger(SectorStocksController.class);

	@Autowired
	SectorStocksService sectorstocksService;
	RestTemplate restTemplate = new RestTemplate();

	@GetMapping(value = "/showCompanies/{sector}")
	public List<SectorStocks> showStocks(@PathVariable String sector) {
		// Returns Companies when sector is passed as an argument.

		logger.info("Fetching Companies belonging to Sector: {}", sector);
		return sectorstocksService.getCompanyBySector(sector);
	}

	@GetMapping(value = "/showCompanySymbol/{sector}")
	public List<String> showCompanySymbols(@PathVariable String sector) {
		// Returns Company Symbols when sector is passed as an argument.

		logger.info("Fetching Company Symbols of Companies belonging to Sector: {}", sector);
		return sectorstocksService.getCompanySymbolBySector(sector);
	}

	@GetMapping(value = "/showSectorWiseChange")
	public List<SectorAvg> showSectorWiseChange() {
		// Returns Sector Wise Comparison on attribute - change.

		logger.info("Finding Sector Wise Avg Growth");
		return sectorstocksService.getSectorWiseGrowth();
	}

	@GetMapping(value = "/showDistinctSectors")
	public List<String> showDistinctSectors() {
		// Returns Distinct sectors.

		logger.info("Finding Distinct Sectors");
		return sectorstocksService.getDistinctSectors();
	}

	public RestTemplate restService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
		return this.restTemplate;
	}

	@GetMapping(value = "/getNews")
	public @ResponseBody Articles[] showNews() throws NullPointerException {
		restService(new RestTemplateBuilder());
		String url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=fe85013235624481abbe65c9f37baf27";
		try {
			return this.restTemplate.exchange(url, HttpMethod.GET, null, NewsData.class).getBody().getArticles();
		} catch (NullPointerException e) {
			return new Articles[0];
		}

	}
}
