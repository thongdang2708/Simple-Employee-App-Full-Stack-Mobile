package com.ltp.backend.exception;

import org.springframework.stereotype.Repository;

public class EmployeeNotFoundWithIdException extends RuntimeException {
    public EmployeeNotFoundWithIdException(String exceptionText) {
        super(exceptionText);
    }
}
