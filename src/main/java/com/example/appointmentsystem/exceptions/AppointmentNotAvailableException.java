package com.example.appointmentsystem.exceptions;

public class AppointmentNotAvailableException extends RuntimeException{

    public AppointmentNotAvailableException(String message) {
        super(message);
    }
    
}
