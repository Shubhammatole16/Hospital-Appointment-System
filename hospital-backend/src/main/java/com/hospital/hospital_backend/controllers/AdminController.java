package com.hospital.hospital_backend.controllers;

import com.hospital.hospital_backend.dtos.AdminDTOs.AdminCreateDoctorRequest;
import com.hospital.hospital_backend.dtos.AdminDTOs.AdminCreatePatientRequest;
import com.hospital.hospital_backend.entities.Role;
import com.hospital.hospital_backend.entities.User;
import com.hospital.hospital_backend.services.AdminService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    // Get all users (non-paginated)
    @GetMapping("/users")
    public List<User> allUsers() {
        return adminService.getAllUsers();
    }

    // Delete user by ID
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
    }

    // Delete appointment by ID
    @DeleteMapping("/appointments/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        adminService.deleteAppointment(id);
    }

    // Delete user only if they match the required role
    @DeleteMapping("/patients/{patientUserId}")
    public void deletePatient(@PathVariable Long patientUserId) {
        adminService.deleteUserByRole(patientUserId, Role.PATIENT);
    }

    // Delete doctor (including related records)
    @DeleteMapping("/doctors/{doctorUserId}")
    public void deleteDoctor(@PathVariable Long doctorUserId) {
        adminService.deleteDoctor(doctorUserId);
    }

    // Get paginated users with sorting
    @GetMapping("/users/paged")
    public Page<User> usersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String dir ) {
        return adminService.getUsersPaged(page, size, sortBy, dir);
    }

    // Get paginated users filtered by role
    @GetMapping("/doctors/paged")
    public Page<User> doctorsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size ) {
        return adminService.getUsersByRolePaged(Role.DOCTOR, page, size);
    }

    // Get paginated users filtered by role
    @GetMapping("/patients/paged")
    public Page<User> patientsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return adminService.getUsersByRolePaged(Role.PATIENT, page, size);
    }

    // Get all users filtered by role
    @GetMapping("/doctors")
    public List<User> getDoctors() {
        return adminService.getUsersByRole(Role.DOCTOR);
    }

    // Get all users filtered by role
    @GetMapping("/patients")
    public List<User> getPatients() {
        return adminService.getUsersByRole(Role.PATIENT);
    }

    // Create new patient by admin
    @PostMapping("/addpatients")
    public User addPatient(@Valid @RequestBody AdminCreatePatientRequest req) {
        return adminService.createPatient(req);
    }

    // Create new doctor by admin
    @PostMapping("/adddoctors")
    public User addDoctor(@Valid @RequestBody AdminCreateDoctorRequest req) {
        return adminService.createDoctor(req);
    }
}