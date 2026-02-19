import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Auth } from '../../services/auth';
import { Appointment } from '../../services/appointment';
import { Admin } from '../../services/admin';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-dashboard',
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {

  // Current active view
  view: 'appointments' | 'doctors' | 'patients' = 'appointments';

  // Appointments pagination
  apptPage = 0;
  apptSize = 5;
  apptTotalPages = 0;
  appointments: any[] = [];

  // Doctors pagination
  docPage = 0;
  docSize = 5;
  docTotalPages = 0;
  doctors: any[] = [];

  // Patients pagination
  patPage = 0;
  patSize = 5;
  patTotalPages = 0;
  patients: any[] = [];

  // Add forms toggle
  showAddPatient = false;
  showAddDoctor = false;

  // Form models
  patientForm: any = {};
  doctorForm: any = {};

  constructor(
    private apptService: Appointment,
    private adminService: Admin,
    private auth: Auth,
    private cdr: ChangeDetectorRef
  ) {}

  // Load default view
  ngOnInit(): void {
    this.openAppointments(); // default
  }

  // Logout admin
  logout() {
    this.auth.logout();
  }

  // ---------- Switch Views ----------

  // Show appointments
  openAppointments() {
    this.view = 'appointments';
    this.apptPage = 0;
    this.loadAppointments();
  }

  // Show doctors
  openDoctors() {
    this.view = 'doctors';
    this.docPage = 0;
    this.loadDoctors();
  }

  // Show patients
  openPatients() {
    this.view = 'patients';
    this.patPage = 0;
    this.loadPatients();
  }

  // ---------- Load Data ----------

  // Load paginated appointments
  loadAppointments() {
    this.apptService.getAllPaged(this.apptPage, this.apptSize, 'id', 'desc')
      .subscribe({
        next: (res) => {
          this.appointments = res.content || [];
          this.apptTotalPages = res.totalPages || 0;
          this.cdr.detectChanges();
        },
        error: (err) => console.log("Appointments error:", err)
      });
  }

  // Load paginated doctors
  loadDoctors() {
    this.adminService.getDoctorsPaged(this.docPage, this.docSize)
      .subscribe({
        next: (res) => {
          this.doctors = res.content || [];
          this.docTotalPages = res.totalPages || 0;
          this.cdr.detectChanges();
        },
        error: (err) => console.log("Doctors error:", err)
      });
  }

  // Load paginated patients
  loadPatients() {
    this.adminService.getPatientsPaged(this.patPage, this.patSize)
      .subscribe({
        next: (res) => {
          this.patients = res.content || [];
          this.patTotalPages = res.totalPages || 0;
          this.cdr.detectChanges();
        },
        error: (err) => console.log("Patients error:", err)
      });
  }

  // ---------- Pagination Controls ----------

  apptPrev(){ if(this.apptPage > 0){ this.apptPage--; this.loadAppointments(); } }
  apptNext(){ if(this.apptPage < this.apptTotalPages - 1){ this.apptPage++; this.loadAppointments(); } }

  docPrev(){ if(this.docPage > 0){ this.docPage--; this.loadDoctors(); } }
  docNext(){ if(this.docPage < this.docTotalPages - 1){ this.docPage++; this.loadDoctors(); } }

  patPrev(){ if(this.patPage > 0){ this.patPage--; this.loadPatients(); } }
  patNext(){ if(this.patPage < this.patTotalPages - 1){ this.patPage++; this.loadPatients(); } }


  // ---------- Delete Operations ----------

  // Delete appointment
  removeAppointment(id: number) {
    if (!confirm("Delete appointment?")) return;
    this.adminService.deleteAppointment(id).subscribe(() => this.loadAppointments());
  }

  // Delete patient
  removePatient(userId: number) {
    if (!confirm("Delete patient? All patient appointments will be deleted.")) return;
    this.adminService.deletePatient(userId).subscribe(() => this.loadPatients());
  }

  // Delete doctor
  removeDoctor(userId: number) {
    if (!confirm("Delete doctor? All doctor appointments will be deleted.")) return;
    this.adminService.deleteDoctor(userId).subscribe(() => this.loadDoctors());
  }

  // ---------- Add Operations ----------
  
  // Open add patient form
  openAddPatient(){ 
    this.showAddPatient = true; this.showAddDoctor = false; 
  }

  // Open add doctor form
  openAddDoctor(){ 
    this.showAddDoctor = true; this.showAddPatient = false; 
  }

  // Add new patient
  addPatient(){
    this.adminService.addPatient(this.patientForm).subscribe(() => {
      alert("Patient added");
      this.patientForm = {};
      this.showAddPatient = false;
      this.loadPatients();
    });
  }

  // Add new doctor
  addDoctor(){
    this.adminService.addDoctor(this.doctorForm).subscribe(() => {
      alert("Doctor added");
      this.doctorForm = {};
      this.showAddDoctor = false;
      this.loadDoctors();
    });
  }
}