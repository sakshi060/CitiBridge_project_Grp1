package com.citi.tradeRecommendation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "UserMaster")
@NoArgsConstructor
public class UserMaster {
	
	@Id
	@Column(name ="userId")
	String userId;
	
	@Column(name ="password")
	String password;
	
}
