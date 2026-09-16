package com.example.appointmentsystem.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.appointmentsystem.exceptions.AppointmentNotFoundException;
import com.example.appointmentsystem.exceptions.ServiceNotFoundException;
import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.services.AppointmentService;
import com.example.appointmentsystem.services.AvailabilityService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/user/appointment")
public class AppointmentController {

   private final AppointmentService appointmentService;
   private final AvailabilityService availabilityService;

   public AppointmentController(AppointmentService appointmentService, AvailabilityService availabilityService) {
      this.appointmentService = appointmentService;
      this.availabilityService = availabilityService;
   }

   // Booking a new appointment
   @PostMapping("/book")
   public String bookAppointment(@RequestParam Long availabilityId,
         HttpSession session, Model model) {

      Long userId = (Long) session.getAttribute("userId");
      if (userId == null) {
         return "redirect:/users/login";
      }

      try {
         appointmentService.bookAppointment(availabilityId, userId);
         model.addAttribute("success", "Your Appointment is booked successfuly");
         model.addAttribute("availablities", availabilityService.getAllAvailabilities());

      } catch (ServiceNotFoundException e) {
         model.addAttribute("error", e.getMessage());
         model.addAttribute("availablities", availabilityService.getAllAvailabilities());
      }

      return "booking";
   }

   // Get all appointments for a specific user
   @GetMapping("/getAppByUser")
   public String getAppointmentForUser(HttpSession session, Model model) {
      Long userId = (Long) session.getAttribute("userId");
      if (userId == null) {
         return "redirect:/users/login";
      }

      try {
         List<Appointment> appointments = appointmentService.getAppointmentByUserId(userId);
         model.addAttribute("appointments", appointments);

      } catch (AppointmentNotFoundException e) {
         // model.addAttribute("error", e.getMessage());
         model.addAttribute("appointments", List.of());
      }

      model.addAttribute("statuses",
            Appointment.Status.values());
      return "user-appointment";
   }

   // Get all appointments
   @GetMapping("/getAppByAdmin")
   public String getAppointmentForAdmin(Model model) {
      try {
         List<Appointment> appointments = appointmentService.getAllAppointment();
         model.addAttribute("appointments", appointments);

      } catch (AppointmentNotFoundException e) {
         model.addAttribute("error", e.getMessage());
      }
      return "admin-appointment";
   }

   // update appointment status
   @PostMapping("/updateStatus")
   public String updateAppointmentStatus(@RequestParam Long id, @RequestParam String status,
         HttpSession session, Model model) {
      Long userId = (Long) session.getAttribute("userId");

      if (userId == null) {
         return "redirect:/users/login";
      }

      try {
         Appointment.Status appointmenStatus = Appointment.Status.valueOf(status);
         appointmentService.updateAppointmentStatus(id, appointmenStatus);
         model.addAttribute("success", "The status has been updated");

      } catch (AppointmentNotFoundException e) {
         model.addAttribute("error", e.getMessage());

      } catch (IllegalArgumentException e) {
         model.addAttribute("error", "Invalid appointment status");
      }

      try {
         model.addAttribute("appointments",
               appointmentService.getAppointmentByUserId(userId));// for not return without the Appointments that have
                                                                  // already showed

      } catch (AppointmentNotFoundException e) {
         model.addAttribute("appointments", List.of());
      }
      model.addAttribute("statuses",
            Appointment.Status.values());

      return "user-appointment";
   }
}
