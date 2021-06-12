package com.citi.tradeRecommendation.model;


import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SectorAvg {
	
	String sector;
	double avggrowth;

}
