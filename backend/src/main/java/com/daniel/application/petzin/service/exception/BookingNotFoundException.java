package com.daniel.application.petzin.service.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Integer id) {
        super("Reserva não encontrada: " + id);
    }
}
