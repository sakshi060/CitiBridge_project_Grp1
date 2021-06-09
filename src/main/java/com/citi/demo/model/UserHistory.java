package com.citi.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "UserHistory")
@AllArgsConstructor
@NoArgsConstructor
public class UserHistory implements Serializable {

	@Override
	public String toString() {
		return "UserHistory [id=" + id + ", companySymbol=" + companySymbol + ", sector=" + sector + ", price=" + price
				+ ", userId=" + userId + ", volume=" + volume + "]";
	}

	private static final AtomicInteger count = new AtomicInteger(0); 
	@Id
	@Column
	int id = count.incrementAndGet()-1 ;
	@Column(name ="companySymbol")
	String companySymbol;
	@Column(name ="sector")
	String sector;
	@Column(name ="price")
	BigDecimal price;
	@Column(name ="userId")
	String userId;
	@Column(name ="volume")
	long volume;
	
	public UserHistory(int id, String companySymbol, String sector, BigDecimal price, String userId, long volume) {
		super();
		this.id = id;
		this.companySymbol = companySymbol;
		this.sector = sector;
		this.price = price;
		this.userId = userId;
		this.volume = volume;
	}
	public UserHistory() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompanySymbol() {
		return companySymbol;
	}
	public void setCompanySymbol(String companySymbol) {
		this.companySymbol = companySymbol;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public long getVolume() {
		return volume;
	}
	public void setVolume(long volume) {
		this.volume = volume;
	}



		
	
	
}