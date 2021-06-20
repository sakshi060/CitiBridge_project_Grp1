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
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public double getAvggrowth() {
		return avggrowth;
	}
	public void setAvggrowth(double avggrowth) {
		this.avggrowth = avggrowth;
	}
	
	
	
}
