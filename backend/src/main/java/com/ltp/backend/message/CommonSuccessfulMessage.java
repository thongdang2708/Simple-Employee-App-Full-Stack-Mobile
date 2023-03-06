package com.ltp.backend.message;

import java.time.LocalDate;

public class CommonSuccessfulMessage {

    private LocalDate timestamp;
    private String message;

    public CommonSuccessfulMessage(String message) {
        this.timestamp = LocalDate.now();
        this.message = message;
    }

    public CommonSuccessfulMessage() {
    }

    public LocalDate getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
