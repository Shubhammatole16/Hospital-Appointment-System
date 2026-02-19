package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.AuthDTOs.LoginRequest;
import com.hospital.hospital_backend.dtos.AuthDTOs.LoginResponse;
import com.hospital.hospital_backend.dtos.AuthDTOs.RegisterRequest;

public interface AuthService {
    // Authenticate user and return login response
    LoginResponse login(LoginRequest req);

    // Register new user and return login response
    LoginResponse register(RegisterRequest req);
}
