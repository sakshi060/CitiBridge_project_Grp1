package com.citi.trade.recommendation.model;

import lombok.Data;

@Data
public class NewsData {
	String status;
	int totalResults;
	Articles[] articles;

}
