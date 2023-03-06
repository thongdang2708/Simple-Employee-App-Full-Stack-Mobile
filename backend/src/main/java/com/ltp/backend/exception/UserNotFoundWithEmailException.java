package com.ltp.backend.exception;

public class UserNotFoundWithEmailException extends RuntimeException {
    public UserNotFoundWithEmailException(String exceptionText) {
        super(exceptionText);
    }
}
