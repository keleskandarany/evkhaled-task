package com.evkhaled.taskapp.controller;

import com.evkhaled.taskapp.dto.ApiResponse;
import com.evkhaled.taskapp.dto.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Math.sqrt;

@RestController
public class AuthController extends BaseController {

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Static validation for emails within domain @evtask.com with password evtaskpassword
        // Static auth of course just for the scope of the code challenge and not valid for a real-world app implementation
        if (!email.endsWith("@engelvoelkers.com") || !password.equals("evtaskpassword")) {
            ApiResponse response = new ApiResponse("Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        ApiResponse response = new ApiResponse("Login successful.");
        return ResponseEntity.ok(response);
    }
}
