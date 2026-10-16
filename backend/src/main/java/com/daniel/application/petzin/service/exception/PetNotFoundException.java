package com.daniel.application.petzin.service.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Integer id) {
        super("Pet não encontrado: " + id);
    }
}
