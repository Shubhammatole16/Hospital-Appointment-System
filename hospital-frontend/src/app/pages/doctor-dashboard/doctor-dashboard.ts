import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Appointment } from '../../services/appointment';
import { Auth } from '../../services/auth';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-doctor-dashboard',
  imports: [CommonModule],
  templateUrl: './doctor-dashboard.html',
  styleUrl: './doctor-dashboard.css',
})
export class DoctorDashboard implements OnInit {

  // Doctor appointments list
  appointments: any[] = [];

  // Logged-in user details
  user: any;

  constructor(
    private appService: Appointment,
    private auth: Auth,
    private cdr: ChangeDetectorRef) {}

  // Initialize dashboard
  ngOnInit(): void {
    this.user = this.auth.getUser();
    this.loadAppointments();
  }

  // Load appointments for logged-in doctor
  loadAppointments() {
    if (!this.user?.doctorId) {
      console.log("doctorId missing in login response", this.user);
      return;
    }
    this.appService.getByDoctor(this.user.doctorId)
      .subscribe((res: any) => {
        this.appointments = res || [];
        this.cdr.detectChanges();
      });
  }

  // Mark appointment as completed
  completeAppointment(id: number) {
    if (!confirm("Mark this appointment as COMPLETED?")) return;

    this.appService.complete(id).subscribe({
      next: () => {
        alert("Appointment marked as COMPLETED");
        this.loadAppointments();
      },
      error: (err) => console.log(err)
    });
  }

// Logout doctor
  logout() {
    this.auth.logout();
  }
}
