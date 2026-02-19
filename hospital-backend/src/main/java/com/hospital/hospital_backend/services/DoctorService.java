package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.DoctorsDTOs.DoctorResponse;
import com.hospital.hospital_backend.entities.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DoctorService {

    // Get all doctors (non-paginated)
    List<DoctorResponse> getAll();

    // Get doctors with pagination and sorting
    Page<Doctor> doctorsPaged(int page, int size, String sortBy, String dir);
}
