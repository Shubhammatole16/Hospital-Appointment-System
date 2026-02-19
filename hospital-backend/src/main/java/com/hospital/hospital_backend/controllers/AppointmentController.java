package com.hospital.hospital_backend.controllers;

import com.hospital.hospital_backend.dtos.AppointmentDTOs.AppointmentCreateRequest;
import com.hospital.hospital_backend.dtos.AppointmentDTOs.AppointmentResponse;
import com.hospital.hospital_backend.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@AllArgsConstructor
@CrossOrigin("*")
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Book a new appointment
    @PostMapping("/book")
    public AppointmentResponse book(@RequestBody AppointmentCreateRequest req) {
        return appointmentService.book(req);
    }

    // Get all appointments (non-paginated)
    @GetMapping("/all")
    public List<AppointmentResponse> all() {
        return appointmentService.getAll();
    }

    // Get appointments by patient ID
    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponse> patient(@PathVariable Long patientId) {
        return appointmentService.getByPatient(patientId);
    }

    // Get appointments by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponse> doctor(@PathVariable Long doctorId) {
        return appointmentService.getByDoctor(doctorId);
    }

    // Cancel an appointment
    @PutMapping("/{id}/cancel")
    public AppointmentResponse cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }

    // Mark an appointment as completed
    @PutMapping("/{id}/complete")
    public AppointmentResponse complete(@PathVariable Long id) {
        return appointmentService.complete(id);
    }

    // Get paginated appointments with sorting
    @GetMapping("/paged")
    public Page<AppointmentResponse> allPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String dir ) {
        return appointmentService.getAllPaged(page, size, sortBy, dir);
    }
}
