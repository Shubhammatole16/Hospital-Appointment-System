package com.hospital.hospital_backend.controllers;

import com.hospital.hospital_backend.dtos.DoctorsDTOs.DoctorResponse;
import com.hospital.hospital_backend.entities.Doctor;
import com.hospital.hospital_backend.services.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@AllArgsConstructor
@CrossOrigin("*")
public class DoctorController {

    private final DoctorService doctorService;

    // Get all doctors (non-paginated)
    @GetMapping
    public List<DoctorResponse> allDoctors() {
        return doctorService.getAll();
    }

    // Get doctors with pagination and sorting
    @GetMapping("/paged")
    public Page<Doctor> doctorsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String dir ) {
        return doctorService.doctorsPaged(page, size, sortBy, dir);
    }
}
