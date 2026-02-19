package com.hospital.hospital_backend.dtos.AuthDTOs;

import com.hospital.hospital_backend.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // PATIENT or DOCTOR (admin can be inserted manually)
}
