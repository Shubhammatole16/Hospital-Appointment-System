import { Component, OnInit } from '@angular/core';
import { Auth } from '../../services/auth';
import { Router, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  imports: [CommonModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar implements OnInit {

  // Logged-in user
  user: any = null;

  constructor(private auth: Auth, private router: Router) {}

  // Load user on init and route change
  ngOnInit(): void {
    this.loadUser();
    this.router.events.pipe(filter(e => e instanceof NavigationEnd))
      .subscribe(() => this.loadUser());
  }

  // Get user from localStorage
  loadUser() {
    this.user = this.auth.getUser();
    if (!this.user || !this.user.role) this.user = null;
  }

  // Check if navbar should show (hide on login/register)
  isLoggedIn() {
    const url = this.router.url;
    if (url.includes('/login') || url.includes('/register')) return false;
    return this.auth.isLoggedIn();
  }

  // Logout user
  logout() {
    this.auth.logout();
  }

  // Navigate to dashboard based on role
  goHome() {
    this.loadUser();
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }

    if (this.user.role === 'ADMIN') this.router.navigate(['/admin']);
    else if (this.user.role === 'PATIENT') this.router.navigate(['/patient']);
    else if (this.user.role === 'DOCTOR') this.router.navigate(['/doctor']);
  }

}
