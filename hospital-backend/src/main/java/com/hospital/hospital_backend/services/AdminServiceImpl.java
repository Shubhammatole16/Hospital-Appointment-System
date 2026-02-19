package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.AdminDTOs.AdminCreateDoctorRequest;
import com.hospital.hospital_backend.dtos.AdminDTOs.AdminCreatePatientRequest;
import com.hospital.hospital_backend.entities.Doctor;
import com.hospital.hospital_backend.entities.Role;
import com.hospital.hospital_backend.entities.User;
import com.hospital.hospital_backend.repositories.AppointmentRepository;
import com.hospital.hospital_backend.repositories.DoctorRepository;
import com.hospital.hospital_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Delete user by ID (with existence check)
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(NOT_FOUND, "User not found");
        }
        userRepository.deleteById(userId);
    }

    // Delete appointment by ID
    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    // Delete user only if they match required role
    public void deleteUserByRole(Long userId, Role requiredRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));

        if (user.getRole() != requiredRole) {
            throw new ResponseStatusException(BAD_REQUEST, "User is not a " + requiredRole);
        }

        if (requiredRole == Role.PATIENT) {
            appointmentRepository.deleteByPatientId(userId);
        }
        userRepository.deleteById(userId);
    }

    // Delete doctor along with related records
    public void deleteDoctor(Long doctorUserId) {
        User doctorUser = userRepository.findById(doctorUserId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor user not found"));

        if (doctorUser.getRole() != Role.DOCTOR) {
            throw new ResponseStatusException(BAD_REQUEST, "User is not a DOCTOR");
        }

        Doctor doctor = doctorRepository.findByUserId(doctorUserId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Doctor record not found"));

        appointmentRepository.deleteByDoctorId(doctor.getId());
        doctorRepository.deleteById(doctor.getId());
        userRepository.deleteById(doctorUserId);
    }

    // Get paginated users with sorting
    public Page<User> getUsersPaged(int page, int size, String sortBy, String dir) {
        Sort sort = dir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable);
    }

    // Get paginated users filtered by role
    public Page<User> getUsersByRolePaged(Role role, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return userRepository.findByRole(role, pageable);
    }

    // Get all users filtered by role
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    // Create new patient by admin
    @Override
    public User createPatient(AdminCreatePatientRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.setRole(Role.PATIENT);

        return userRepository.save(u);
    }

    // Create new doctor by admin
    @Override
    public User createDoctor(AdminCreateDoctorRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.setRole(Role.DOCTOR);

        User savedUser = userRepository.save(u);

        Doctor d = new Doctor();
        d.setName(req.getName());
        d.setSpecialization(req.getSpecialization());
        d.setUser(savedUser);

        doctorRepository.save(d);

        return savedUser;
    }
}