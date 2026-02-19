import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Appointment {
  
  // Base API URL
  private api = "http://localhost:8080/api/appointments";

  constructor(private http: HttpClient) {}

  // Book appointment
  book(data: any) {
    return this.http.post(`${this.api}/book`, data);
  }

  // Get all appointments
  getAll() {
    return this.http.get(`${this.api}/all`);
  }

  // Get all doctors
  getDoctors() {
    return this.http.get<any[]>("http://localhost:8080/api/doctors");
  }

  // Get appointments by patient ID
  getByPatient(id: number) {
    return this.http.get(`${this.api}/patient/${id}`);
  }

  // Get appointments by doctor ID
  getByDoctor(id: number) {
    return this.http.get(`${this.api}/doctor/${id}`);
  }

  // Cancel appointment
  cancel(id: number) {
    return this.http.put(`${this.api}/${id}/cancel`, {});
  }

  // Get paginated appointments
  getAllPaged(page = 0, size = 5, sortBy = 'id', dir = 'desc') {
    return this.http.get<any>(`${this.api}/paged?page=${page}&size=${size}&sortBy=${sortBy}&dir=${dir}`);
  }

  // Delete appointment (admin)
  deleteAppointment(id: number) {
    return this.http.delete(`http://localhost:8080/api/admin/appointments/${id}`);
  }

  // Mark appointment as completed
  complete(id: number) {
    return this.http.put(`${this.api}/${id}/complete`, {});
  }
}

