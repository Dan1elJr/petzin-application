package com.daniel.application.petiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.repository.PetOwnerRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class PetOwnerService {

    private final PetOwnerRepository petOwnerRepository;

    public PetOwnerService(PetOwnerRepository petOwnerRepository) {
        this.petOwnerRepository = petOwnerRepository;
    }

    public List<PetOwner> findAll() {
        return petOwnerRepository.findAll();
    }

    public PetOwner findById(Integer id) {
        return petOwnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tutor não encontrado: " + id));
    }

    @Transactional
    public PetOwner save(PetOwner petOwner) {
        if (petOwner.getId() != null) {
            findById(petOwner.getId());
        }

        return petOwnerRepository.save(petOwner);
    }

    @Transactional
    public void deleteById(Integer id) {
        PetOwner petOwner = findById(id);
        petOwnerRepository.delete(petOwner);
    }
}
