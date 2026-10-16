package com.daniel.application.petzin.service.exception;

public class PetNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public PetNotFoundException(Integer id) {
        super("Pet não encontrado: " + id);
    }
}
