package com.daniel.application.petiz.service.exception;

public class PetOwnerNotFoundException extends RuntimeException {
    public PetOwnerNotFoundException(Integer id) {
        super("Tutor não encontrado: " + id);
    }
}
