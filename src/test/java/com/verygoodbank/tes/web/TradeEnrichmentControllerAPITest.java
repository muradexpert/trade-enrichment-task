package com.verygoodbank.tes.web;

import com.verygoodbank.tes.web.controller.TradeEnrichmentController;
import com.verygoodbank.tes.web.domain.model.TradeDTO;
import com.verygoodbank.tes.web.service.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeEnrichmentController.class)
 class TradeEnrichmentControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
     void testEnrichTradeData_withValidCsv() throws Exception {
        String csvContent = "date,product_id,currency,price\n" +
                "2024-08-22,1,USD,100.5\n" +
                "2024-08-23,2,EUR,200.75";
        MockMultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        List<TradeDTO> mockEnrichedData = List.of(
                new TradeDTO("2024-08-22", "Product A", "USD", 100.5),
                new TradeDTO("2024-08-23", "Product B", "EUR", 200.75)
        );

        when(tradeService.enrichTrades(file)).thenReturn(mockEnrichedData);

        String expectedResponse = "date,product_name,currency,price\n" +
                "2024-08-22,Product A,USD,100.5\n" +
                "2024-08-23,Product B,EUR,200.75";

        mockMvc.perform(multipart("/api/v1/enrich")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
     void testEnrichTradeData_withEmptyCsv() throws Exception {
        String csvContent = "date,product_id,currency,price\n";
        MockMultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        when(tradeService.enrichTrades(file)).thenReturn(List.of());

        String expectedResponse = "date,product_name,currency,price\n";

        mockMvc.perform(multipart("/api/v1/enrich")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
     void testEnrichTradeData_withMalformedCsv() throws Exception {
        String csvContent = "date,product_id,currency,price\n" +
                "2024-08-22,1,USD,abc";
        MockMultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        when(tradeService.enrichTrades(file)).thenReturn(List.of());

        String expectedResponse = "date,product_name,currency,price\n";

        mockMvc.perform(multipart("/api/v1/enrich")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
