import { Component, OnInit, ChangeDetectorRef  } from '@angular/core';
import { Appointment } from '../../services/appointment';
import { Auth } from '../../services/auth';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-patient-dashboard',
  imports: [FormsModule, CommonModule],
  templateUrl: './patient-dashboard.html',
  styleUrl: './patient-dashboard.css',
})
export class PatientDashboard implements OnInit {

  // Patient appointments
  appointments: any[] = [];

  // Booking form data
  form: any = {};

  // Logged-in user
  user: any;

  // Doctors list
  doctors:any[] = [];

  // Search text for filtering doctors
  searchText: string = '';

  constructor(
    public auth: Auth,
    private appService: Appointment,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {}

  // Initialize dashboard
  ngOnInit() {
    this.user = this.auth.getUser();
    this.loadAppointments();
    this.appService.getDoctors().subscribe(res => this.doctors = res);
  }

  // Toggle booking form
  showBookForm: boolean = false;

  openBookForm() {
    this.showBookForm = true;
  }

  closeBookForm() {
    this.showBookForm = false;
    this.form = {};
  }

  // Load doctors list
  loadDoctors() {
    this.appService.getDoctors().subscribe({
      next: (res: any) => {
        this.doctors = res;
        this.cdr.detectChanges();
      },
      error: (err) => console.log("Doctor API error:", err)
    });
  }

  // Load appointments for logged-in patient
  loadAppointments() {
    if (!this.user?.id) {
      console.log("User id missing, cannot load appointments");
      return;
    }

    this.appService.getByPatient(this.user.id).subscribe({
      next: (res: any) => {
        console.log("Appointments response:", res);
        this.appointments = res || [];
        this.cdr.detectChanges();
      },
      error: (err) => console.log("Appointments load error:", err)
    });
  }

  // Book new appointment
  book() {
    this.form.patientId = this.user.id;
    this.form.doctorId = Number(this.form.doctorId);

    this.appService.book(this.form).subscribe({
      next: () => {
        alert("Appointment Booked!");
        this.loadAppointments();
        this.closeBookForm(); // ✅ hide after booking
      },
      error: (err) => console.log("Book error:", err)
    });
  }

  // Cancel appointment
  cancelAppointment(id: number) {
    if (!confirm("Cancel this appointment?")) return;

    this.appService.cancel(id).subscribe({
      next: () => {
        alert("Appointment cancelled");
        this.loadAppointments();
      },
      error: (err: any) => console.log(err)
    });
  }

  // Logout patient
  logout() {
    this.auth.logout();
  }
}