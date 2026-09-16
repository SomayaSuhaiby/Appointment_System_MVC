package com.example.appointmentsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.appointmentsystem.exceptions.AppointmentNotFoundException;
import com.example.appointmentsystem.exceptions.ServiceNotFoundException;
import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.model.Availability;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repositories.AppointmentRepository;
import com.example.appointmentsystem.repositories.AvailabilityRepo;
import com.example.appointmentsystem.repositories.UserRepository;

@Service
public class AppointmentService {
   private final AppointmentRepository appointmentRepository;
   private final UserRepository userRepository;
   private final AvailabilityRepo availabilityRepo;

   public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository,
         AvailabilityRepo availabilityRepo) {
      this.appointmentRepository = appointmentRepository;
      this.userRepository = userRepository;
      this.availabilityRepo = availabilityRepo;
   }

   // Booking a new appointment
   public void bookAppointment(Long availabilityId, Long userId) {

      Optional<User> user = userRepository.findById(userId);

      Availability availableService = availabilityRepo.findById(availabilityId)
            .orElseThrow(() -> new ServiceNotFoundException("Service is not found"));

      Appointment appointment = new Appointment();
      appointment.setUser(user.get());
      appointment.setService(availableService.getService());
      appointment.setAppointment_time(availableService.getStart_time());

      appointmentRepository.save(appointment);
   }

   // Get all appointment
   public List<Appointment> getAllAppointment() {
      List<Appointment> appointments = appointmentRepository.findAll();

      if (appointments.isEmpty()) {
         throw new AppointmentNotFoundException("There is no any appointment");
      }
      return appointments;
   }

   // Get all appointment for currently user
   public List<Appointment> getAppointmentByUserId(Long userId) {
      List<Appointment> appointments = appointmentRepository.findByUser_Id(userId);
      if (appointments.isEmpty()) {
         throw new AppointmentNotFoundException("There is no any appointment");

      }
      return appointments;
   }

   // Update status of an appointment
   public void updateAppointmentStatus(Long id, Appointment.Status status) {
      Appointment appointment = appointmentRepository.findById(id)
            .orElseThrow(() -> new AppointmentNotFoundException("This appointment is not found"));

      appointment.setStatus(status);
      appointmentRepository.save(appointment);
   }
}
