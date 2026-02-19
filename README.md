# 🏥 Hospital Appointment Management System

A full-stack web application for managing hospital appointments with role-based dashboards for Admin, Doctor, and Patient.

---

## 🚀 Tech Stack

### 🔧 Backend
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- RESTful APIs

### 🎨 Frontend
- Angular
- Bootstrap 5
- TypeScript

---

## 👥 Roles & Features

### 🔐 Authentication
- User Registration & Login
- Role-based access control (Admin / Doctor / Patient)
- Dynamic Navbar rendering based on user role

---

### 👨‍💼 Admin Module
- Add Doctor (with specialization)
- Add Patient
- Delete Doctor (removes associated appointments)
- Delete Patient (removes associated appointments)
- Delete Appointments
- View all Doctors, Patients & Appointments
- Pagination support
- Dashboard statistics overview

---

### 👨‍⚕️ Doctor Module
- View assigned appointments
- Mark appointments as **COMPLETED**
- Track appointment status (Booked / Cancelled / Completed)

---

### 🧑 Patient Module
- Book multiple appointments
- Cancel booked appointments
- View appointment history

---

## 🔄 Appointment Lifecycle

BOOKED → CANCELLED  
BOOKED → COMPLETED  

---

## 🗂 Database Schema

- **users** (id, name, email, password, role)
- **doctors** (id, name, specialization, user_id)
- **appointments** (id, patient_id, doctor_id, date, time, status, reason)

---

## ⚙️ Setup Instructions

### 🖥 Backend Setup

1. Configure MySQL in `application.properties`:
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db;
   spring.datasource.username=root;
   spring.datasource.password=Shubham@1631;
   spring.jpa.hibernate.ddl-auto=update;
   
3. Run the Spring Boot application:
  mvn spring-boot:run

Backend runs at: `http://localhost:8080`
Frontend runs at: `http://localhost:4200`


---

## 📸 Screenshots

- Login Page
- Admin Dashboard
- Doctor Dashboard
- Patient Dashboard
- Pagination
- Add Doctor Modal

---

## 🎯 Key Highlights

- Clean REST API architecture
- DTO-based data transfer
- Role-based dynamic UI rendering
- Pagination implementation
- Proper entity relationships using JPA
- Full CRUD operations for Admin
- Responsive Bootstrap UI

---

## 📌 Future Enhancements

- JWT Authentication
- Email notifications
- Calendar integration
- Docker deployment
- Production hosting

---

## 👨‍💻 Author

Your Name: Shubham Matole
GitHub: https://github.com/Shubhammatole16





   
