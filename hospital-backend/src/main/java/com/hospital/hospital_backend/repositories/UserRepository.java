package com.hospital.hospital_backend.repositories;

import com.hospital.hospital_backend.entities.Role;
import com.hospital.hospital_backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (used for authentication)
    Optional<User> findByEmail(String email);

    // Get all users by role (PATIENT, DOCTOR, ADMIN)
    List<User> findByRole(Role role);

    // Get users by role with pagination support
    Page<User> findByRole(Role role, Pageable pageable);
}