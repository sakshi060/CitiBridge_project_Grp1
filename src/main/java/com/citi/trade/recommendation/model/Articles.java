package com.citi.trade.recommendation.model;

import java.util.Date;

import lombok.Data;

@Data
public class Articles {

	String author;
	String content;
	String description;
	Date publishedDate;
	NewsSource source;
	String title;
	String url;
	String urlToImage;

}
