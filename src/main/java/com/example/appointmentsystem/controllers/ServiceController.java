package com.example.appointmentsystem.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.appointmentsystem.DTOs.ServiceDTO;
import com.example.appointmentsystem.exceptions.ServiceNotFoundException;
import com.example.appointmentsystem.exceptions.UserNotFoundException;
import com.example.appointmentsystem.model.ServiceModel;
import com.example.appointmentsystem.services.ServiceModelService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/services")
public class ServiceController {

  private final ServiceModelService serviceModelService;

  public ServiceController(ServiceModelService serviceModelService) {
    this.serviceModelService = serviceModelService;
  }

  // Get all services for a specific provider/admin
  @GetMapping("/getServices")
  public String getServicesByProviderId(HttpSession session, Model model) {

    Long providerId = (Long) session.getAttribute("userId");
    List<ServiceModel> services = serviceModelService.getServicesByProviderId(providerId);

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
  public String createService(@Valid @ModelAttribute ServiceDTO dto,
      BindingResult result, HttpSession session, Model model) {
    if (result.hasErrors()) {
      return "createService";// createService.html
    }
    Long providerId = (Long) session.getAttribute("userId");
    if (providerId == null) {
      return "redirect:/users/login";
    }
    try {
      serviceModelService.createService(dto, providerId);
      model.addAttribute("success", "Service created successfully");

    } catch (UserNotFoundException e) {
      model.addAttribute("error", e.getMessage());
    }
    return "createService";// createService.html
  }

  @GetMapping("/delete")
  public String showDelete() {
    return "admin";
  }

  // Delete a specific service by ID
  @PostMapping("/delete")
  public String deleteSevice(@RequestParam("serviceId") Long serviceId, Model model) {

    try {
      serviceModelService.deleteSevice(serviceId);
      model.addAttribute("success", "This service has been deleted successfuly");

    } catch (ServiceNotFoundException e) {
      model.addAttribute("error", e.getMessage());

    }
    return "services";
  }

}
