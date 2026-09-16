package com.example.appointmentsystem.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.appointmentsystem.exceptions.AvailableServicesNotFoundException;
import com.example.appointmentsystem.model.Availability;
import com.example.appointmentsystem.repositories.AvailabilityRepo;

@Service
public class AvailabilityService {

    private final AvailabilityRepo availabilityRepo;

    AvailabilityService(AvailabilityRepo availabilityRepo) {
        this.availabilityRepo = availabilityRepo;
    }

    // Get all availble services
    public List<Availability> getAllAvailabilities() {
        List<Availability> availabilities = availabilityRepo.findAll();
        if (availabilities.isEmpty()) {
            throw new AvailableServicesNotFoundException("There is no available services");
        }
        return availabilities;
    }

    // Get available services for a specific provider
    public List<Availability> getAvailabilitiesForProvider(Long Id) {
        List<Availability> availabilities = availabilityRepo.findByServiceProviderId(Id);
        if (availabilities.isEmpty()) {
            throw new AvailableServicesNotFoundException("There is no available services");
        }
        return availabilities;
    }
}