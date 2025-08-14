package com.example.appointmentsystem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.appointmentsystem.model.Availability;
import com.example.appointmentsystem.services.AvailabilityService;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    // Get all available services for user
    @GetMapping("/list")
    public String getAvailableServices(@ModelAttribute Availability availabilty, Model model) {
        List<Availability> availablities = availabilityService.getAllAvailabilities();
        model.addAttribute("availablities", availablities);
        return "booking";
    }

    // Get all availble services for admin
    @GetMapping("/listForServiceProvider")
    public String getAvailableServicesForServiceProvider(@ModelAttribute Availability availabilty, Model model) {
        List<Availability> availablities = availabilityService.getAllAvailabilities();
        model.addAttribute("availablities", availablities);
        return "availableSrvForServiceProvider";
    }

}
