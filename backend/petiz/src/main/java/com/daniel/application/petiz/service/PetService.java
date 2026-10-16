package com.daniel.application.petiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.repository.PetRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Integer id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado: " + id));
    }

    @Transactional
    public Pet save(Pet pet) {
  
        if (pet.getId() != null) {
            findById(pet.getId());
        }

        return petRepository.save(pet);
    }

    @Transactional
    public void deleteById(Integer id) {
        Pet pet = findById(id);
        petRepository.delete(pet);
    }
}
