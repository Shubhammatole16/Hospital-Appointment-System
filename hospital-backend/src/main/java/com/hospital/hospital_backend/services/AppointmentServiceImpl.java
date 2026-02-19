package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.AppointmentDTOs.AppointmentCreateRequest;
import com.hospital.hospital_backend.dtos.AppointmentDTOs.AppointmentResponse;
import com.hospital.hospital_backend.entities.*;
import com.hospital.hospital_backend.repositories.AppointmentRepository;
import com.hospital.hospital_backend.repositories.DoctorRepository;
import com.hospital.hospital_backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    // Book a new appointment
    @Override
    public AppointmentResponse book(AppointmentCreateRequest req) {

        User patient = userRepository.findById(req.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patient.getRole() != Role.PATIENT) {
            throw new RuntimeException("Only PATIENT can be booked as patient");
        }

        Doctor doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment a = new Appointment();
        a.setPatient(patient);
        a.setDoctor(doctor);
        a.setAppointmentDate(req.getAppointmentDate());
        a.setAppointmentTime(req.getAppointmentTime());
        a.setReason(req.getReason());
        a.setStatus(AppointmentStatus.BOOKED);

        return toResponse(appointmentRepository.save(a));
    }

    // Get all appointments (non-paginated)
    @Override
    public List<AppointmentResponse> getAll() {
        return appointmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    // Get appointments by patient ID
    @Override
    public List<AppointmentResponse> getByPatient(Long id) {
        return appointmentRepository.findByPatientId(id).stream().map(this::toResponse).toList();
    }

    // Get appointments by doctor ID
    @Override
    public List<AppointmentResponse> getByDoctor(Long id) {

        return appointmentRepository.findByDoctorId(id).stream().map(this::toResponse).toList();
    }
    // Convert Appointment entity to AppointmentResponse DTO
    private AppointmentResponse toResponse(Appointment a) {
        AppointmentResponse r = new AppointmentResponse();
        r.setId(a.getId());

        r.setPatientId(a.getPatient().getId());
        r.setPatientName(a.getPatient().getName());

        r.setDoctorId(a.getDoctor().getId());
        r.setDoctorName(a.getDoctor().getName());
        r.setSpecialization(a.getDoctor().getSpecialization());

        r.setAppointmentDate(a.getAppointmentDate());
        r.setAppointmentTime(a.getAppointmentTime());

        r.setReason(a.getReason());
        r.setStatus(a.getStatus());
        return r;
    }

    // Cancel an appointment
    @Override
    public AppointmentResponse cancel(Long appointmentId) {

        Appointment a = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        a.setStatus(AppointmentStatus.CANCELLED);

        return toResponse(appointmentRepository.save(a));
    }

    // Get paginated appointments with sorting
    @Override
    public Page<AppointmentResponse> getAllPaged(int page, int size, String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return appointmentRepository.findAll(pageable)
                .map(this::toResponse);
    }

    // Mark an appointment as completed
    @Override
    public AppointmentResponse complete(Long appointmentId) {
        Appointment a = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        a.setStatus(AppointmentStatus.COMPLETED);

        return toResponse(appointmentRepository.save(a));
    }
}