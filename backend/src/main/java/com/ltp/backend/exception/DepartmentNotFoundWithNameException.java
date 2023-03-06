package com.ltp.backend.exception;

public class DepartmentNotFoundWithNameException extends RuntimeException {
    public DepartmentNotFoundWithNameException(String exceptionText) {
        super(exceptionText);
    }
}
