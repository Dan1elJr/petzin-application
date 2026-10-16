package com.daniel.application.petiz.service.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Integer id) {
        super("Pet não encontrado: " + id);
    }
}
