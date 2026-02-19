package com.hospital.hospital_backend.services;

import com.hospital.hospital_backend.dtos.AppointmentDTOs.AppointmentCreateRequest;
import com.hospital.hospital_backend.dtos.AppointmentDTOs.AppointmentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AppointmentService {

    // Book a new appointment
    AppointmentResponse book(AppointmentCreateRequest req);

    // Get all appointments (non-paginated)
    List<AppointmentResponse> getAll();

    // Get appointments by patient ID
    List<AppointmentResponse> getByPatient(Long patientId);

    // Get appointments by doctor ID
    List<AppointmentResponse> getByDoctor(Long doctorId);

    // Cancel an appointment
    AppointmentResponse cancel(Long appointmentId);

    // Mark an appointment as completed
    AppointmentResponse complete(Long appointmentId);

    // Get paginated appointments with sorting
    Page<AppointmentResponse> getAllPaged(int page, int size, String sortBy, String dir);
}