package com.verygoodbank.tes.web.util;

import com.verygoodbank.tes.web.domain.model.Trade;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class CsvUtilsTest {

    @Test
     void testParseTrades_withValidData() throws Exception {
        String csvContent = "date,product_id,currency,price\n" +
                            "20160101,1,EUR,10.0\n" +
                            "20160102,2,USD,20.5";
        
        MultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        List<Trade> trades = CsvUtils.parseTrades(file);

        assertEquals(2, trades.size());
        assertEquals(new Trade("20160101", "1", "EUR", 10.0), trades.get(0));
        assertEquals(new Trade("20160102", "2", "USD", 20.5), trades.get(1));
    }

    @Test
     void testParseTrades_withInvalidCsvFormat() {
        String csvContent = "date,product_id,currency,price\n" +
                            "20160101,1,EUR\n" +
                            "20160102,2,USD,20.5";
        
        MultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        Exception exception = assertThrows(Exception.class, () -> CsvUtils.parseTrades(file));
        assertEquals("Invalid CSV format, expected 4 columns but got: 3", exception.getMessage());
    }

    @Test
     void testParseTrades_withInvalidNumberFormat() {
        String csvContent = "date,product_id,currency,price\n" +
                            "20160101,1,EUR,not_a_number\n" +
                            "20160102,2,USD,20.5";
        
        MultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        Exception exception = assertThrows(Exception.class, () -> CsvUtils.parseTrades(file));
        assertEquals("Invalid number format for price: not_a_number", exception.getMessage());
    }

    @Test
     void testParseTrades_withEmptyFile() throws Exception {
        String csvContent = "date,product_id,currency,price\n";
        
        MultipartFile file = new MockMultipartFile("file", "trades.csv", "text/csv", csvContent.getBytes());

        List<Trade> trades = CsvUtils.parseTrades(file);

        assertEquals(0, trades.size());
    }
}
