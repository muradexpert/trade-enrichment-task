package com.verygoodbank.tes.web.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private DateUtils() {
    }

    public static LocalDate parseDate(String text, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(text, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Failed to parse date: " + text, text, e.getErrorIndex(), e);
        }
    }

}
