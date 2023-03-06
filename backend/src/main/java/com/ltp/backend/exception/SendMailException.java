package com.ltp.backend.exception;

public class SendMailException extends RuntimeException {
    public SendMailException(String exceptionText) {
        super(exceptionText);
    }
}
