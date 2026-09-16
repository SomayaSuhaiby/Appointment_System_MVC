package com.example.appointmentsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.appointmentsystem.model.Availability;

public interface AvailabilityRepo extends JpaRepository<Availability, Long> {
    List<Availability> findByServiceProviderId(Long id);
}
