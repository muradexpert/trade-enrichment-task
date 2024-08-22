package com.verygoodbank.tes.web.exception;

public class InvalidCsvFormatException extends RuntimeException {
    public InvalidCsvFormatException(String message) {
        super(message);
    }
}