package com.verygoodbank.tes.web.controller;

import com.verygoodbank.tes.web.domain.model.TradeDTO;
import com.verygoodbank.tes.web.service.TradeService;
import com.verygoodbank.tes.web.util.ResponseUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class TradeEnrichmentControllerTest {

    @Mock
    private TradeService tradeService;

    @InjectMocks
    private TradeEnrichmentController tradeEnrichmentController;

    @Test
     void testEnrichTradeData() {
        String csvContent = "date,product_id,currency,price\n" +
                            "20160101,1,EUR,10\n" +
                            "20160101,2,EUR,20.1\n" +
                            "20160101,33,EUR,30.34\n" +
                            "20160101,11,EUR,35.34\n";

        MultipartFile file = new MockMultipartFile(
            "file",
            "trades.csv",
            "text/csv",
            csvContent.getBytes(StandardCharsets.UTF_8)
        );

        List<TradeDTO> mockEnrichedData = Arrays.asList(
            new TradeDTO("20160101", "Treasury Bills Domestic", "EUR", 10.0),
            new TradeDTO("20160101", "Corporate Bonds Domestic", "EUR", 20.1),
            new TradeDTO("20160101", "REPO Domestic", "EUR", 30.34),
            new TradeDTO("20160101", "766A_CORP BD", "EUR", 35.34)
        );
        when(tradeService.enrichTrades(file)).thenReturn(mockEnrichedData);

        ResponseEntity<String> response = tradeEnrichmentController.enrichTradeData(file);

        String expectedResponse = ResponseUtils.getString(mockEnrichedData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }
}
