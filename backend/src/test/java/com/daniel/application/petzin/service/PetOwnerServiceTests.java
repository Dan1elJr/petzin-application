package com.daniel.application.petzin.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import com.daniel.application.petzin.repository.PetOwnerRepository;
import com.daniel.application.petzin.service.exception.PetOwnerNotFoundException;

class PetOwnerServiceTests {

    @Test
    void shouldThrowExceptionWhenPetOwnerDoesNotExist() {
        // Arrange
        PetOwnerRepository repository = mock(PetOwnerRepository.class);
        PetOwnerService service = new PetOwnerService(repository);
        when(repository.findById(1)).thenReturn(Optional.empty());

        // Act
        Executable acao = () -> service.findByIdDTO(1);

        // Assert
        assertThrows(PetOwnerNotFoundException.class, acao);
    }
}
