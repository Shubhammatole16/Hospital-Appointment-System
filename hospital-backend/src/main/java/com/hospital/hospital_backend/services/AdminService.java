package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.AdminDTOs.AdminCreateDoctorRequest;
import com.hospital.hospital_backend.dtos.AdminDTOs.AdminCreatePatientRequest;
import com.hospital.hospital_backend.entities.Role;
import com.hospital.hospital_backend.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminService {

    // Get all users (non-paginated)
    List<User> getAllUsers();

    // Delete user by ID
    void deleteUser(Long userId);

    // Delete appointment by ID
    void deleteAppointment(Long appointmentId);

    // Delete user only if they match the required role
    void deleteUserByRole(Long userId, Role requiredRole);

    // Delete doctor (including related records)
    void deleteDoctor(Long doctorUserId);

    // Get paginated users with sorting
    Page<User> getUsersPaged(int page, int size, String sortBy, String dir);

    // Get paginated users filtered by role
    Page<User> getUsersByRolePaged(Role role, int page, int size);

    // Get all users filtered by role
    List<User> getUsersByRole(Role role);

    // Create new patient by admin
    User createPatient(AdminCreatePatientRequest req);

    // Create new doctor by admin
    User createDoctor(AdminCreateDoctorRequest req);
}
