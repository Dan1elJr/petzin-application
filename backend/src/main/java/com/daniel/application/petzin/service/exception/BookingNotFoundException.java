package com.daniel.application.petzin.service.exception;

public class BookingNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public BookingNotFoundException(Integer id) {
        super("Reserva não encontrada: " + id);
    }
}
