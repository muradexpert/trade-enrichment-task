package com.verygoodbank.tes.web.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleInvalidTradeDataException() {
        InvalidTradeDataException ex = new InvalidTradeDataException("Invalid data");

        ResponseEntity<String> response = globalExceptionHandler.handleInvalidTradeDataException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid data", response.getBody());
    }

    @Test
    void testHandleInvalidCsvFormatException() {
        InvalidCsvFormatException ex = new InvalidCsvFormatException("Invalid CSV format");

        ResponseEntity<String> response = globalExceptionHandler.handleInvalidCsvFormatException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid CSV format", response.getBody());
    }

    @Test
    void testHandleInvalidPriceFormatException() {
        InvalidPriceFormatException ex = new InvalidPriceFormatException("Invalid price format");

        ResponseEntity<String> response = globalExceptionHandler.handleInvalidPriceFormatException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid price format", response.getBody());
    }
}
