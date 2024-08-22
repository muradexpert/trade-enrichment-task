package com.verygoodbank.tes.web.util;

import com.verygoodbank.tes.web.domain.model.TradeDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseUtils {
    private static final String HEADER = "date,product_name,currency,price\n";

    private ResponseUtils() {
    }


    public static String getString(List<TradeDTO> enrichedData) {
        if (enrichedData == null || enrichedData.isEmpty()) {
            return HEADER;
        }

        return HEADER.concat(enrichedData.stream()
                .map(trade -> String.join(",", trade.date(), trade.productName(), trade.currency(),
                        String.valueOf(trade.price()))).collect(Collectors.joining("\n")));
    }
}
