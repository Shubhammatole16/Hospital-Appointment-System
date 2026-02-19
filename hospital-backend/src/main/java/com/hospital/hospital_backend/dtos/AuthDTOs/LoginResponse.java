package com.hospital.hospital_backend.dtos.AuthDTOs;

import com.hospital.hospital_backend.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Long doctorId;
}