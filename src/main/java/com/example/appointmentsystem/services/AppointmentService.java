package com.example.appointmentsystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.repositories.AppointmentRepository;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    // Get all appointment
    public List<Appointment> getAllAppointment() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments;
    }

}
