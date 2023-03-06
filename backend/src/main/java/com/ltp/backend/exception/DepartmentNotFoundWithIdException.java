package com.ltp.backend.exception;

public class DepartmentNotFoundWithIdException extends RuntimeException {
    public DepartmentNotFoundWithIdException(String exceptionText) {
        super(exceptionText);
    }
}
