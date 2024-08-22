package com.verygoodbank.tes.web.service;

import com.verygoodbank.tes.web.domain.model.Trade;
import com.verygoodbank.tes.web.domain.model.TradeDTO;
import com.verygoodbank.tes.web.util.CsvUtils;
import com.verygoodbank.tes.web.util.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
 class TradeServiceTest {

    @InjectMocks
    private TradeService tradeService;

    @Mock
    private MultipartFile multipartFile;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testEnrichTrades_withValidTrades() {
        Trade trade1 = new Trade("20160101", "1", "EUR", 10.0);
        Trade trade2 = new Trade("20160102", "2", "USD", 20.1);
        List<Trade> trades = Arrays.asList(trade1, trade2);

        try (MockedStatic<CsvUtils> csvUtilsMockedStatic = mockStatic(CsvUtils.class);
             MockedStatic<DateUtils> dateUtilsMockedStatic = mockStatic(DateUtils.class)) {

            csvUtilsMockedStatic.when(() -> CsvUtils.parseTrades(any(MultipartFile.class)))
                    .thenReturn(trades);

            dateUtilsMockedStatic.when(() -> DateUtils.parseDate(any(String.class), any(DateTimeFormatter.class)))
                    .thenReturn(LocalDate.now());

            List<TradeDTO> enrichedTrades = tradeService.enrichTrades(multipartFile);

            assertEquals(2, enrichedTrades.size());
            assertEquals("Missing Product Name", enrichedTrades.get(0).productName());
            assertEquals("Missing Product Name", enrichedTrades.get(1).productName());
        }
    }

    @Test
     void testEnrichTrades_withInvalidDate() throws Exception {

        Trade trade1 = new Trade("invalid-date", "1", "EUR", 10.0);
        List<Trade> trades = Collections.singletonList(trade1);

        try (MockedStatic<CsvUtils> csvUtilsMockedStatic = mockStatic(CsvUtils.class);
             MockedStatic<DateUtils> dateUtilsMockedStatic = mockStatic(DateUtils.class)) {

            csvUtilsMockedStatic.when(() -> CsvUtils.parseTrades(any(MultipartFile.class)))
                    .thenReturn(trades);

            dateUtilsMockedStatic.when(() -> DateUtils.parseDate(eq("invalid-date"), any(DateTimeFormatter.class)))
                    .thenThrow(new RuntimeException("Invalid date"));

            List<TradeDTO> enrichedTrades = tradeService.enrichTrades(multipartFile);

            assertEquals(0, enrichedTrades.size());
        }
    }

    @Test
     void testEnrichTrades_withMissingProduct()  {
        Trade trade1 = new Trade("20160101", "999", "EUR", 10.0);
        List<Trade> trades = Collections.singletonList(trade1);

        try (MockedStatic<CsvUtils> csvUtilsMockedStatic = mockStatic(CsvUtils.class);
             MockedStatic<DateUtils> dateUtilsMockedStatic = mockStatic(DateUtils.class)) {

            csvUtilsMockedStatic.when(() -> CsvUtils.parseTrades(any(MultipartFile.class)))
                    .thenReturn(trades);

            dateUtilsMockedStatic.when(() -> DateUtils.parseDate(any(String.class), any(DateTimeFormatter.class)))
                    .thenReturn(LocalDate.now());

            List<TradeDTO> enrichedTrades = tradeService.enrichTrades(multipartFile);

            assertEquals(1, enrichedTrades.size());
            assertEquals("Missing Product Name", enrichedTrades.get(0).productName());
        }
    }
}
