package com.hospital.hospital_backend.repositories;

import com.hospital.hospital_backend.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find doctor record using associated User ID
    Optional<Doctor> findByUserId(Long userId);
}
