package com.example.appointmentsystem.exceptions;

public class InvalidAppointmentStatusException extends RuntimeException{

    public InvalidAppointmentStatusException(String message) {
        super(message);
    }
    
}
