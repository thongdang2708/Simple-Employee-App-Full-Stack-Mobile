package com.ltp.backend.exception;

public class ManagerNotFoundWithIdException extends RuntimeException {
    public ManagerNotFoundWithIdException(String exceptionText) {
        super(exceptionText);
    }
}
