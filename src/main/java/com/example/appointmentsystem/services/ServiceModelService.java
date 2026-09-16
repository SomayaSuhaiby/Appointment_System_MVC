package com.example.appointmentsystem.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.appointmentsystem.DTOs.ServiceDTO;
import com.example.appointmentsystem.exceptions.ServiceNotFoundException;
import com.example.appointmentsystem.exceptions.UserNotFoundException;
import com.example.appointmentsystem.model.ServiceModel;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repositories.ServiceRepository;
import com.example.appointmentsystem.repositories.UserRepository;

@Service
public class ServiceModelService {

  private final ServiceRepository serviceRepository;
  private final UserRepository userRepository;

  public ServiceModelService(ServiceRepository serviceRepository, UserRepository userRepository) {
    this.serviceRepository = serviceRepository;
    this.userRepository = userRepository;
  }

  // Create a new service
  public void createService(ServiceDTO dto, Long providerId) {

    User serviceProvider = userRepository.findById(providerId)
        .orElseThrow(() -> new UserNotFoundException("service provider is not found"));

    ServiceModel service = new ServiceModel();
    service.setName(dto.getName());
    service.setDescription(dto.getDescription());
    service.setPrice(dto.getPrice());
    service.setServiceProvider(serviceProvider);

    serviceRepository.save(service);
  }

  // Get all services for a specific provider/admin
  public List<ServiceModel> getServicesByProviderId(Long providerId) {

    return serviceRepository.findByServiceProvider_Id(providerId);
  }

  // Delete a specific service by ID
  public void deleteSevice(Long serviceId) {

    ServiceModel service = serviceRepository.findById(serviceId)
        .orElseThrow(() -> new ServiceNotFoundException("This Service is not found"));

    serviceRepository.delete(service);

  }
}
