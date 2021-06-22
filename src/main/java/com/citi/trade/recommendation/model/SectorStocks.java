package com.citi.trade.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "SectorStocks")
@AllArgsConstructor

public class SectorStocks {
    @Id
    @Column(name = "companySymbol")
    String companySymbol;
    @Column(name = "companyName")
    String companyName;
    @Column(name = "sector")
    String sector;

}
