package com.citi.trade.recommendation.model;


import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Service
public class SectorAvg {
	
	String sector;
	double avggrowth;
	
}
