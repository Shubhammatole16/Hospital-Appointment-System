package com.hospital.hospital_backend.dtos.DoctorsDTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorResponse {
    private Long id;
    private String name;
    private String specialization;
}