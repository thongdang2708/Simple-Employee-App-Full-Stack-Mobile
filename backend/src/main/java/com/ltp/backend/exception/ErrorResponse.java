package com.ltp.backend.exception;

import java.time.LocalDate;
import java.util.List;

public class ErrorResponse {

    private LocalDate timestamp;
    private List<String> messages;

    public ErrorResponse(List<String> messages) {
        this.timestamp = LocalDate.now();
        this.messages = messages;
    }

    public ErrorResponse() {
    }

    public LocalDate getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

}
