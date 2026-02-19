package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.AuthDTOs.LoginRequest;
import com.hospital.hospital_backend.dtos.AuthDTOs.LoginResponse;
import com.hospital.hospital_backend.dtos.AuthDTOs.RegisterRequest;
import com.hospital.hospital_backend.entities.Doctor;
import com.hospital.hospital_backend.entities.Role;
import com.hospital.hospital_backend.entities.User;
import com.hospital.hospital_backend.repositories.DoctorRepository;
import com.hospital.hospital_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    // Register new user and return login response
    @Override
    public LoginResponse register(RegisterRequest req) {

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.setRole(req.getRole());

        User saved = userRepository.save(u);

        if (saved.getRole() == Role.DOCTOR) {
            Doctor d = new Doctor();
            d.setName(saved.getName());
            d.setSpecialization("GENERAL");
            d.setUser(saved);
            doctorRepository.save(d);
        }
        return toLoginResponse(saved);
    }

    // Authenticate user and return login response
    @Override
    public LoginResponse login(LoginRequest req) {

        User u = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email/password"));

        if (!u.getPassword().equals(req.getPassword())) {
            throw new RuntimeException("Invalid email/password");
        }
        return toLoginResponse(u);
    }
    // Convert User entity to LoginResponse DTO
    private LoginResponse toLoginResponse(User u) {
        LoginResponse res = new LoginResponse();
        res.setId(u.getId());
        res.setName(u.getName());
        res.setEmail(u.getEmail());
        res.setRole(u.getRole());

        if (u.getRole() == Role.DOCTOR) {
            doctorRepository.findByUserId(u.getId())
                    .ifPresent(d -> res.setDoctorId(d.getId()));
        }
        return res;
    }
}