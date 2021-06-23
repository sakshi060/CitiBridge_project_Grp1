package com.citi.trade.recommendation.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingParameterListTest {

    @Test
    void values() {
        Assertions.assertEquals(3,SortingParameterList.values().length);
    }

    @Test
    void valueOf() {
        Assertions.assertEquals("PE_RATIO", SortingParameterList.valueOf("PE_RATIO").toString());
    }
}