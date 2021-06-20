package com.citi.trade.recommendation.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;


@Data
@NoArgsConstructor
@Service
public class SectorAvg {

    String sector;
    double avggrowth;

}
