package com.verygoodbank.tes.web.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

 class DateUtilsTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Test
     void testParseDate_withValidDate() {
        String dateString = "20160101";

        LocalDate date = DateUtils.parseDate(dateString, FORMATTER);

        assertEquals(LocalDate.of(2016, 1, 1), date);
    }

    @Test
     void testParseDate_withInvalidDate() {
        String dateString = "2016-01-01";

        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> 
            DateUtils.parseDate(dateString, FORMATTER)
        );

        assertEquals("Failed to parse date: 2016-01-01", exception.getMessage());
    }

    @Test
     void testParseDate_withEmptyString() {
        String dateString = "";

        DateTimeParseException exception = assertThrows(DateTimeParseException.class, () -> 
            DateUtils.parseDate(dateString, FORMATTER)
        );

        assertEquals("Failed to parse date: ", exception.getMessage());
    }
}
