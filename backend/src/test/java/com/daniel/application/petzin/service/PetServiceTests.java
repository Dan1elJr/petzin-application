package com.daniel.application.petzin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.daniel.application.petzin.dto.PetDTO;
import com.daniel.application.petzin.entity.Pet;
import com.daniel.application.petzin.entity.PetOwner;
import com.daniel.application.petzin.repository.PetRepository;

class PetServiceTests {

    @Test
    void shouldReturnPetById() {
        // Arrange
        PetRepository repository = mock(PetRepository.class);
        PetService service = new PetService(repository);
        PetOwner owner = new PetOwner();
        owner.setId(2);
        Pet pet = new Pet(1, "Rex", "Labrador", owner);
        when(repository.findById(1)).thenReturn(Optional.of(pet));

        // Act
        PetDTO resultado = service.findByIdDTO(1);

        // Assert
        assertEquals(new PetDTO(1, "Rex", "Labrador", 2), resultado);
    }
}
