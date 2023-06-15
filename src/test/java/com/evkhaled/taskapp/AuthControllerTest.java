package com.evkhaled.taskapp;

import com.evkhaled.taskapp.controller.AuthController;
import com.evkhaled.taskapp.dto.ApiResponse;
import com.evkhaled.taskapp.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthControllerTest {

    private final AuthController authController = new AuthController();

    @Test
    public void testLoginWithValidCredentials() {
        // Prepare test data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test1@engelvoelkers.com");
        loginRequest.setPassword("evtaskpassword");

        // Perform the login request
        ResponseEntity<ApiResponse> response = authController.login(loginRequest);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful.", Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        // Prepare test data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid@example.com");
        loginRequest.setPassword("wrongpassword");

        // Perform the login request
        ResponseEntity<ApiResponse> response = authController.login(loginRequest);

        // Verify the response
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid credentials.", Objects.requireNonNull(response.getBody()).getMessage());
    }
}