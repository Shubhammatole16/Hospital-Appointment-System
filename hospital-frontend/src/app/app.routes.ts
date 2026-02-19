import { Routes } from '@angular/router';
import { Register } from './pages/register/register';
import { Login } from './pages/login/login';
import { AdminDashboard } from './pages/admin-dashboard/admin-dashboard';
import { PatientDashboard } from './pages/patient-dashboard/patient-dashboard';
import { DoctorDashboard } from './pages/doctor-dashboard/doctor-dashboard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'admin', component: AdminDashboard },
  { path: 'patient', component: PatientDashboard },
  { path: 'doctor', component: DoctorDashboard }
];
