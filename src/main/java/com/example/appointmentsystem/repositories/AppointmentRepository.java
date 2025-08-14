package com.example.appointmentsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.appointmentsystem.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser_Id(Long userId);

    List<Appointment> findByServiceId(Long serviceId);
}
