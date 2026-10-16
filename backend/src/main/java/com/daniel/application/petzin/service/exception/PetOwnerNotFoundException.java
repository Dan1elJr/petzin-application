package com.daniel.application.petzin.service.exception;

public class PetOwnerNotFoundException extends RuntimeException {
    public PetOwnerNotFoundException(Integer id) {
        super("Tutor não encontrado: " + id);
    }
}
