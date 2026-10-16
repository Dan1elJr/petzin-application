package com.daniel.application.petzin.service.exception;

public class PetOwnerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public PetOwnerNotFoundException(Integer id) {
        super("Tutor não encontrado: " + id);
    }
}
