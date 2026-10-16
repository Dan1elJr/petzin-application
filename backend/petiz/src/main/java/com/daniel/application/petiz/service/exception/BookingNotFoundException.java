package com.daniel.application.petiz.service.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException(Integer id) {
        super("Reserva não encontrada: " + id);
    }
}
