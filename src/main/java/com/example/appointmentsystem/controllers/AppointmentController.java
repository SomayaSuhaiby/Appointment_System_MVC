package com.example.appointmentsystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.model.ServiceModel;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repositories.AppointmentRepository;
import com.example.appointmentsystem.repositories.ServiceRepository;
import com.example.appointmentsystem.repositories.UserRepository;
import com.example.appointmentsystem.services.AppointmentService;
import com.example.appointmentsystem.services.AvailabilityService;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/user/appointment")
public class AppointmentController {

   @Autowired
   private AppointmentRepository appointmentRepository;

   @Autowired
   private UserRepository userRepository;
   @Autowired
   private ServiceRepository serviceRepository;
   @Autowired
   private AvailabilityService availabilityService;
   @Autowired
   private AppointmentService appointmentService;

   @GetMapping("/booking")
   public String showBookForm() {
      return "user";
   }

   // Booking a new appointment
   @PostMapping("/bookingAppointment")
   public String bookAppointment(@ModelAttribute Appointment appointment, Model model) {

      // ............user id.............................
      if (appointment.getUser() == null || appointment.getUser().getId() == null) {
         model.addAttribute("error", "user Id is required");
         return "booking";
      }
      Optional<User> user = userRepository.findById(appointment.getUser().getId());
      if (user.isEmpty()) {
         model.addAttribute("error", "user Id is not found");
         model.addAttribute("availablities", availabilityService.getAllAvailabilities());
         return "booking";
      }
      appointment.setUser(user.get());

      // ...........service id .................
      if (appointment.getService() == null || appointment.getService().getId() == null) {
         model.addAttribute("error", "Service Id is required");
         model.addAttribute("availablities", availabilityService.getAllAvailabilities());

         return "booking";
      }
      Optional<ServiceModel> service = serviceRepository.findById(appointment.getService().getId());
      if (service.isEmpty()) {
         model.addAttribute("error", "Service Id is not found");

         model.addAttribute("availablities", availabilityService.getAllAvailabilities());
         return "booking";
      }
      appointment.setService(service.get());
      // ************************************************************************** */
      appointmentRepository.save(appointment);
      model.addAttribute("success", "Your Appointment is booked successfuly");
      model.addAttribute("availablities", availabilityService.getAllAvailabilities());

      return "booking";
   }

   // Get all appointments for a specific  user
   @GetMapping("/getAppByUser")
   public String getAppointmentForUser(HttpSession session, Model model) {
      Long userId = (Long) session.getAttribute("userId");
      List<Appointment> appointments = appointmentRepository.findByUser_Id(userId);
      if (appointments.isEmpty()) {
         model.addAttribute("error", "there is no appointment for this user");

      }
      model.addAttribute("appointments", appointments);
      return "user-appointment";
   }

   // Get all appointments
   @GetMapping("/getAppByAdmin")
   public String getAppointmentForAdmin(Model model) {
      List<Appointment> appointments = appointmentService.getAllAppointment();
      if (appointments.isEmpty()) {
         model.addAttribute("error", "there is no appointment for this user");

      }
      model.addAttribute("appointments", appointments);
      return "admin-appointment";
   }

   @GetMapping("/update")
   public String showUpdate() {
      return "update_status";
   }

   // update appointment status(confirmed,cancelled)
   @PostMapping("/update")
   public String updateAppointmentStatus(@RequestParam Long id, @RequestParam String status, Model model) {
      Appointment appointment = appointmentRepository.findById(id).orElse(null);
      if (appointment != null) {
         appointment.setStatus(Appointment.Status.valueOf(status));
         appointmentRepository.save(appointment);
         model.addAttribute("success", "The status has been updated");
         model.addAttribute("appointments", appointmentService.getAllAppointment());// for not return without the
                                                                                    // Appointments that have already
                                                                                    // showed
         return "update_status";
      }
      model.addAttribute("error", "This appointment is not found");
      model.addAttribute("appointments", appointmentService.getAllAppointment());
      return "update_status";
   }
}
