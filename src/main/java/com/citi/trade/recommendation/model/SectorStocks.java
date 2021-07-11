package com.citi.trade.recommendation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "SectorStocks")
@AllArgsConstructor
@NoArgsConstructor

public class SectorStocks {
	@Id
	@Column(name = "companySymbol")
	String companySymbol;
	@Column(name = "companyName")
	String companyName;
	@Column(name = "sector")
	String sector;

}
