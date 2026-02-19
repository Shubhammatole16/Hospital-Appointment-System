import { Component } from '@angular/core';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  // Login form data
  form: any = {};

  constructor(private auth: Auth, private router: Router) {}

  // Handle login
  login() {
    this.auth.login(this.form).subscribe(res => {
      this.auth.saveUser(res);

      // Redirect based on role
      if (res.role === "ADMIN")
        this.router.navigate(['/admin']);
      else if (res.role === "PATIENT")
        this.router.navigate(['/patient']);
      else
        this.router.navigate(['/doctor']);
    });
  }

  // Navigate to register page
  goRegister() {
    this.router.navigate(['/register']); 
  }
}