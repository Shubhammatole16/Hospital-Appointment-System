package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.DoctorsDTOs.DoctorResponse;
import com.hospital.hospital_backend.entities.Doctor;
import com.hospital.hospital_backend.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    // Get all doctors (non-paginated)
    @Override
    public List<DoctorResponse> getAll() {
        return doctorRepository.findAll().stream().map(d -> {
            DoctorResponse r = new DoctorResponse();
            r.setId(d.getId());
            r.setName(d.getName());
            r.setSpecialization(d.getSpecialization());
            return r;
        }).toList();
    }

    // Get doctors with pagination and sorting
    @Override
    public Page<Doctor> doctorsPaged(int page, int size, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return doctorRepository.findAll(pageable);
    }
}
