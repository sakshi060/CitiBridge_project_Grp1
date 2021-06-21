package com.citi.trade.recommendation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SectorAvg {

    String sector;
    double avgGrowth;

}
