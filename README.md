# 🗓️ Appointment System

A Spring Boot web application for managing users, services, availability, and appointments. This system supports user registration/login, service creation, availability listings, and a complete appointment booking workflow with admin approval.

---

##  Features

###  User Management
- Secure registration and login
- Role-based access (Admin/User)
- Password encryption with Spring Security

###  Admin Capabilities
- Create, view, and delete services
- View all availability slots
- View and update all appointments (status: Pending, Confirmed, Cancelled)

###  User Capabilities
- Register & login
- View available services
- Book appointments
- View their booked appointments

---

##  Technologies Used

- Java 17+
- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA
- Spring MVC (Thymeleaf)
- MySQL
- Maven

---

##  Project Structure Overview

###  Key Packages:
- `controllers` – Web endpoints for users, services, availability, and appointments
- `repositories` – Spring Data JPA interfaces for database operations
- `model` – Entity classes like `User`, `ServiceModel`, `Appointment`, `Availability`
- `services` – Business logic for availability and appointments

---

##  Role-Based Access

| Role  | Access |
|-------|--------|
| User  | Register, login, view services, book appointments |
| Admin |Register, login, view services, create/delete services, view/update all appointments |

---

##  Endpoints Overview

###  Authentication

| Method | URL               | Description            |
|--------|-------------------|------------------------|
| GET    | `/users/register` | Show registration page |
| POST   | `/users/register` | Handle user registration |
| GET    | `/users/login`    | Show login page        |
| POST   | `/users/login`    | Handle login & redirect |

---

### 🛠️ Services (Admin Only)

| Method | URL                            | Description                       |
|--------|--------------------------------|-----------------------------------|
| GET    | `/services/create`             | Show service creation form        |
| POST   | `/services/create`             | Create a new service              |
| GET    | `/services/getServices`        | List services for a provider      |
| POST   | `/services/delete`             | Delete a service by ID            |

---

###  Availability

| Method | URL                                  | Description                          |
|--------|--------------------------------------|--------------------------------------|
| GET    | `/availability/list`                 |List availability for users (booking) |
| GET    | `/availability/listForServiceProvider` | List for admins/providers          |

---

###  Appointments

| Method | URL                                  | Description                            |
|--------|--------------------------------------|----------------------------------------|
| GET    | `/user/appointment/booking`          | Show booking page                      |
| POST   | `/user/appointment/bookingAppointment` | Book a new appointment               |
| GET    | `/user/appointment/getAppByUser`     | View user's appointments               |
| GET    | `/user/appointment/getAppByAdmin`    | View all appointments (Admin)         |
| POST   | `/user/appointment/update`           | Update appointment status (Admin)     |

---
