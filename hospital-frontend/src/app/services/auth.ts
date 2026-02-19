import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  
  // Base API URL
  private api = "http://localhost:8080/api/auth";

  constructor(private http: HttpClient, private router: Router) {}

  // Login user
  login(data: any) {
    return this.http.post<any>(`${this.api}/login`, data);
  }

  // Register user
  register(data: any) {
    return this.http.post<any>(`${this.api}/register`, data);
  }

  // Save logged-in user to localStorage
  saveUser(user: any) {
    localStorage.setItem("user", JSON.stringify(user));
  }

  // Get logged-in user from localStorage
  getUser() {
    if (typeof window !== 'undefined' && window.localStorage) {
      const user = localStorage.getItem('user');
      return user ? JSON.parse(user) : null;
    }
    return null;
  }

  // Logout user
  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  // Check if user is logged in
  isLoggedIn() {
    if (typeof window !== 'undefined' && window.localStorage) {
      return !!localStorage.getItem('user');
    }
    return false;
  }
}

