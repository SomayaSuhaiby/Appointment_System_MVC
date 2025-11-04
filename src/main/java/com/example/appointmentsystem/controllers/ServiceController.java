package com.example.appointmentsystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.appointmentsystem.model.ServiceModel;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repositories.ServiceRepository;
import com.example.appointmentsystem.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/services")
public class ServiceController {

  @Autowired
  private ServiceRepository serviceRepository;

  @Autowired
  private UserRepository userRepository;

  // Get all services for a specific provider/admin
  @GetMapping("/getServices")
  public String getServicesByProvider(HttpSession session, Model model) {
    Long providerId = (Long) session.getAttribute("userId");
    List<ServiceModel> services = serviceRepository.findByServiceProvider_Id(providerId);

    model.addAttribute("services", services);// services to fetch it in html
    return "services"; // services.html

  }

  // show create page
  @GetMapping("/create")
  public String showCreate() {
    return "createService";// createService.html
  }

  // create a new service
  @PostMapping("/create")
  public String createService(@ModelAttribute ServiceModel service, Model model) {

    if (service.getServiceProvider() == null || service.getServiceProvider().getId() == null) {

      model.addAttribute("error", "service provider Id is required");
      return "createService";// createService.html
    }
    Optional<User> serviceProvider = userRepository.findById(service.getServiceProvider().getId());
    if (serviceProvider.isEmpty()) {
      model.addAttribute("error", "service provider is not found");
      return "createService";// createService.html
    }

    service.setServiceProvider(serviceProvider.get());
    serviceRepository.save(service);
    model.addAttribute("success", "Service created successfully");
    return "createService";// createService.html
  }

  @GetMapping("/delete")
  public String showDelete() {
    return "admin";
  }

  // Delete a specific service by ID
  @PostMapping("/delete")
  public String deleteSevice(@RequestParam("serviceId") Long serviceId, Model model) {
    Optional<ServiceModel> service = serviceRepository.findById(serviceId);
    if (!service.isPresent()) {
      model.addAttribute("error", "This Service is not found");
      return "admin";

    }

    serviceRepository.delete(service.get());
    model.addAttribute("success", "This service has been deleted successfuly");
    return "admin";
  }

}
