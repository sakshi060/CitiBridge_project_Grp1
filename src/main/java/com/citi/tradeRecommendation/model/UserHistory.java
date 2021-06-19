package com.citi.tradeRecommendation.model;

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
	
}
