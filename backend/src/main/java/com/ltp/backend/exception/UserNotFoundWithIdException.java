package com.ltp.backend.exception;

public class UserNotFoundWithIdException extends RuntimeException {
    public UserNotFoundWithIdException(String exceptionText) {
        super(exceptionText);
    }
}
