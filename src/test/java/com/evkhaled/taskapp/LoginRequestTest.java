package com.evkhaled.taskapp;

import com.evkhaled.taskapp.dto.LoginRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginRequestTest {

    private static Validator validator;

    @BeforeAll
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("valid@example.com");
        loginRequest.setPassword("validpassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidEmailFormat() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("invalid-email");
        loginRequest.setPassword("validpassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertEquals(1, violations.size());
        ConstraintViolation<LoginRequest> violation = violations.iterator().next();
        assertEquals("Invalid email format", violation.getMessage());
    }

    @Test
    public void testEmailFromInvalidDomain() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@test.com");
        loginRequest.setPassword("validpassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertEquals(1, violations.size());
        ConstraintViolation<LoginRequest> violation = violations.iterator().next();
        assertEquals("Emails from test.com are not allowed", violation.getMessage());
    }

    @Test
    public void testEmptyEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("validpassword");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertEquals(1, violations.size());
        ConstraintViolation<LoginRequest> violation = violations.iterator().next();
        assertEquals("Email is required", violation.getMessage());
    }

    @Test
    public void testEmptyPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("valid@example.com");
        loginRequest.setPassword("");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        assertEquals(1, violations.size());
        ConstraintViolation<LoginRequest> violation = violations.iterator().next();
        assertEquals("Password is required", violation.getMessage());
    }
}