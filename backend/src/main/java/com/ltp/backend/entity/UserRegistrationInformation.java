package com.ltp.backend.entity;

import com.ltp.backend.validator.CheckPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationInformation {

    @NotBlank(message = "Username must not be blank!")
    @Size(min = 2, message = "Username must be at least 2 characters!")
    private String username;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email is not valid. Please check again!")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @CheckPassword(message = "Password is not in a correct form. Please check again!")
    private String password;

    public UserRegistrationInformation(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRegistrationInformation() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
