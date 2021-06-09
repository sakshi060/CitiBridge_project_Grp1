package com.citi.demo.model;

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
	@Column(name ="companySymbol")
	String companySymbol;
	@Column(name ="companyName")
	String companyName;
	@Column(name ="sector")
	String sector;	
	
	public String getCompanySymbol() {
		return companySymbol;
	}
	public void setCompanySymbol(String companySymbol) {
		this.companySymbol = companySymbol;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public SectorStocks() {
		super();
	}
	public SectorStocks(String companySymbol, String companyName, String sector) {
		super();
		this.companySymbol = companySymbol;
		this.companyName = companyName;
		this.sector = sector;
	}
	@Override
	public String toString() {
		return "SectorStocks [companySymbol=" + companySymbol + ", companyName=" + companyName + ", sector=" + sector
				+ "]";
	}


}
