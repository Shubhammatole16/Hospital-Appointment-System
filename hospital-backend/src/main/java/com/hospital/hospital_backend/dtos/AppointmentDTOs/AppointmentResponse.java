package com.hospital.hospital_backend.dtos.AppointmentDTOs;

import com.hospital.hospital_backend.entities.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;
    private String specialization;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    private String reason;
    private AppointmentStatus status;
}
