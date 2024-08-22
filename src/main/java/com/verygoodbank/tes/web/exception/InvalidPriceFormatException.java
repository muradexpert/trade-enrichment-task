package com.verygoodbank.tes.web.exception;

public class InvalidPriceFormatException extends RuntimeException {
    public InvalidPriceFormatException(String message) {
        super(message);
    }
}