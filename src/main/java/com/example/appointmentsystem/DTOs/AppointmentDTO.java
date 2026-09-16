package com.example.appointmentsystem.DTOs;

import java.time.LocalDateTime;


import jakarta.validation.constraints.NotNull;

public class AppointmentDTO {

    @NotNull(message = "Service Id is required")
    private Long serviceId;

    @NotNull
    private LocalDateTime appointmentTime;

    

    public Long getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getAppointment_time() {
        return this.appointmentTime;
    }

    public void setAppointment_time(LocalDateTime appointment_time) {
        this.appointmentTime = appointment_time;
    }


}
