package com.ltp.backend.entity;

import jakarta.validation.constraints.NotBlank;

public class RefreshToken {

    @NotBlank(message = "Refresh token is missing. Please fill in!")
    private String refreshToken;

    public RefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshToken() {
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
