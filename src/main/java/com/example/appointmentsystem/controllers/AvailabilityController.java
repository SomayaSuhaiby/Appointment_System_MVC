package com.example.appointmentsystem.controllers;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.appointmentsystem.exceptions.AvailableServicesNotFoundException;
import com.example.appointmentsystem.model.Availability;
import com.example.appointmentsystem.services.AvailabilityService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    
    // Get all available services for user
    @GetMapping("/list")
    public String getAvailableServices(Model model) {
        try {
             List<Availability> availablities = availabilityService.getAllAvailabilities();
        model.addAttribute("availablities", availablities);
        } catch (AvailableServicesNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
       
        return "booking";
    }

    // Get all availble services for admin
    @GetMapping("/listForServiceProvider")
    public String getAvailableServicesForServiceProvider(@ModelAttribute Availability availabilty,
        HttpSession session, Model model) {
            Long providerId=(Long) session.getAttribute("userId");
            
            try {
                List<Availability> availablities = availabilityService.getAvailabilitiesForProvider(providerId);
        model.addAttribute("availablities", availablities);
           } catch (AvailableServicesNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        }
        
        return "availableServiceForProvider";
    }

}
