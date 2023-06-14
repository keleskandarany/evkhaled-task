package com.evkhaled.taskapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class LoginRequest {
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Pattern(regexp = "^((?!@test\\.com).)*$", message = "Emails from test.com are not allowed") //Can also be separated into a custom validator, but seems too much for our purpose ATM
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
