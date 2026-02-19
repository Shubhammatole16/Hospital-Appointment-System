package com.hospital.hospital_backend.controllers;

import com.hospital.hospital_backend.dtos.AuthDTOs.LoginRequest;
import com.hospital.hospital_backend.dtos.AuthDTOs.LoginResponse;
import com.hospital.hospital_backend.dtos.AuthDTOs.RegisterRequest;
import com.hospital.hospital_backend.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    // Register new user and return login response
    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterRequest req) {
        return authService.register(req);
    }

    // Authenticate user and return login response
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }
}
