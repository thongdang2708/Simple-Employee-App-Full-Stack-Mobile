package com.ltp.backend.exception;

public class TokenMissingException extends RuntimeException {
    public TokenMissingException(String exceptionText) {
        super(exceptionText);
    }
}
