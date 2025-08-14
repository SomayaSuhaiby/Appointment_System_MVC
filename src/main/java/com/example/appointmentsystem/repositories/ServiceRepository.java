package com.example.appointmentsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.appointmentsystem.model.ServiceModel;

public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
    List<ServiceModel> findByServiceProvider_Id(Long serviceProviderId);

}
