import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Admin {

  // Base API URL
  private api = "http://localhost:8080/api/admin";

  constructor(private http: HttpClient) {}

  // Get all doctors
  getDoctors() {
    return this.http.get<any[]>(`${this.api}/doctors`);
  }

  // Get all patients
  getPatients() {
    return this.http.get<any[]>(`${this.api}/patients`);
  }

  // Get paginated doctors
  getDoctorsPaged(page = 0, size = 5) {
    return this.http.get<any>(
      `${this.api}/doctors/paged?page=${page}&size=${size}`
    );
  }

  // Get paginated patients
  getPatientsPaged(page = 0, size = 5) {
    return this.http.get<any>(
      `${this.api}/patients/paged?page=${page}&size=${size}`
    );
  }

  // Delete appointment
  deleteAppointment(id: number) {
    return this.http.delete(`${this.api}/appointments/${id}`);
  }

  // Delete patient
  deletePatient(patientUserId: number) {
    return this.http.delete(`${this.api}/patients/${patientUserId}`);
  }

  // Delete doctor
  deleteDoctor(doctorUserId: number) {
    return this.http.delete(`${this.api}/doctors/${doctorUserId}`);
  }

  // Add new patient
  addPatient(data: any) {
    return this.http.post("http://localhost:8080/api/admin/addpatients", data);
  }

  // Add new doctor
  addDoctor(data: any) {
    return this.http.post("http://localhost:8080/api/admin/adddoctors", data);
  }
}
