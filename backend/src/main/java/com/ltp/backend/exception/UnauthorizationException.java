package com.ltp.backend.exception;

public class UnauthorizationException extends RuntimeException {
    public UnauthorizationException(String exceptionText) {
        super(exceptionText);
    }
}
