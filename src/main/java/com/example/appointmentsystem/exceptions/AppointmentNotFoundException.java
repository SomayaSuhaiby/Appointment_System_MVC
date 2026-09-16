package com.example.appointmentsystem.exceptions;

public class AppointmentNotFoundException extends RuntimeException{

    public AppointmentNotFoundException(String message) {
        super(message);
    }
    
}
