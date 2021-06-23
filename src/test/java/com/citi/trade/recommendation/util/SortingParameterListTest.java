package com.citi.trade.recommendation.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class SortingParameterListTest {

    @Test
    void values() {
        Assertions.assertEquals(3,SortingParameterList.values().length);
    }

    @Test
    void valueOf() {
        Assertions.assertEquals("PE_RATIO", SortingParameterList.valueOf("PE_RATIO").toString());
        Assertions.assertEquals("CHANGE", SortingParameterList.valueOf("CHANGE").toString());
        Assertions.assertEquals("MARKET_CAP", SortingParameterList.valueOf("MARKET_CAP").toString());
    }
}