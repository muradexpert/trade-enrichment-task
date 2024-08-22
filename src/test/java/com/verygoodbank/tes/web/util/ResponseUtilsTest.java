package com.verygoodbank.tes.web.util;

import com.verygoodbank.tes.web.domain.model.TradeDTO;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class ResponseUtilsTest {

    private static final String HEADER = "date,product_name,currency,price\n";

    @Test
     void testGetString_withNullList() {
        String result = ResponseUtils.getString(null);
        assertEquals(HEADER, result);
    }

    @Test
     void testGetString_withEmptyList() {
        String result = ResponseUtils.getString(Collections.emptyList());

        assertEquals(HEADER, result);
    }

    @Test
     void testGetString_withSingleTradeDTO() {
        TradeDTO tradeDTO = new TradeDTO("2024-08-22", "Product A", "USD", 100.5);

        String result = ResponseUtils.getString(List.of(tradeDTO));

        String expected = HEADER + "2024-08-22,Product A,USD,100.5";

        assertEquals(expected, result);
    }

    @Test
     void testGetString_withMultipleTradeDTOs() {
        TradeDTO tradeDTO1 = new TradeDTO("2024-08-22", "Product A", "USD", 100.5);
        TradeDTO tradeDTO2 = new TradeDTO("2024-08-23", "Product B", "EUR", 200.75);

        String result = ResponseUtils.getString(List.of(tradeDTO1, tradeDTO2));

        String expected = HEADER +
                "2024-08-22,Product A,USD,100.5\n" +
                "2024-08-23,Product B,EUR,200.75";

        assertEquals(expected, result);
    }
}
