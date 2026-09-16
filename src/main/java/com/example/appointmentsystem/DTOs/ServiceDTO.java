package com.example.appointmentsystem.DTOs;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServiceDTO {

    @NotBlank(message = "Service name is required")
    private String name;

    @NotNull(message = "Descrption is required")
    private String description;

    @NotNull(message = "Price is Required")
    private BigDecimal price;

    @NotNull(message = "Provider Id is required")
    private Long serviceProviderId;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getServiceProviderId() {
        return this.serviceProviderId;
    }

    public void setServiceProviderId(Long serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    
}
