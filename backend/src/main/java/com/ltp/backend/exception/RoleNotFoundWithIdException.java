package com.ltp.backend.exception;

public class RoleNotFoundWithIdException extends RuntimeException {
    public RoleNotFoundWithIdException(String exceptionText) {
        super(exceptionText);
    }
}
