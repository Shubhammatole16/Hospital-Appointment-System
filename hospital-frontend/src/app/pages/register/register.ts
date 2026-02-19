import { Component } from '@angular/core';
import { Auth } from '../../services/auth';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

  // Registration form data (default role = PATIENT)
  form: any = {
    role: 'PATIENT'
  };

  constructor(
    private auth: Auth,
    private router: Router
  ) {}

  // Handle user registration
  register() {
    this.auth.register(this.form).subscribe({
      next: () => {
        alert("Registration Successful!");
        this.router.navigate(['/login']);
      },
      error: (err) => {
        alert("Registration Failed");
        console.log(err);
      }
    });
  }

  // Navigate back to login page
  goLogin() {
    this.router.navigate(['/login']);
  }
}