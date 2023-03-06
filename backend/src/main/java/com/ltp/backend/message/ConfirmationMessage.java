package com.ltp.backend.message;

public class ConfirmationMessage {

    private String username;

    private String content;

    public ConfirmationMessage(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public ConfirmationMessage() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
