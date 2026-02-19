package com.hospital.hospital_backend.repositories;

import com.hospital.hospital_backend.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Find appointments by patient ID
    List<Appointment> findByPatientId(Long patientId);

    // Find appointments by doctor ID
    List<Appointment> findByDoctorId(Long doctorId);

    // Delete all appointments of a specific patient
    void deleteByPatientId(Long patientId);

    // Delete all appointments of a specific doctor
    void deleteByDoctorId(Long doctorId);
}
