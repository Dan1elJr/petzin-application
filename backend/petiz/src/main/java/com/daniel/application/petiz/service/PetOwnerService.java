package com.daniel.application.petiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.application.petiz.dto.PetOwnerDTO;
import com.daniel.application.petiz.dto.mapping.PetOwnerMapper;
import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.repository.PetOwnerRepository;

import com.daniel.application.petiz.service.exception.PetOwnerNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service

public class PetOwnerService {

    private final PetOwnerRepository petOwnerRepository;

    @PersistenceContext
    private EntityManager entityManager;
	
    @Autowired
    public PetOwnerService(PetOwnerRepository petOwnerRepository) {
        this.petOwnerRepository = petOwnerRepository;
    }

    @Transactional(readOnly = true)
    public List<PetOwnerDTO> findAllDTO() {
        return petOwnerRepository.findAll().stream().map(PetOwnerMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public PetOwnerDTO findByIdDTO(Integer id) {
        return PetOwnerMapper.toDTO(findById(id));
    }

    @Transactional
    public PetOwnerDTO create(PetOwnerDTO dto) {
        PetOwner petOwner = PetOwnerMapper.toEntity(dto);
        petOwner.setId(null);
        return PetOwnerMapper.toDTO(save(petOwner));
    }

    @Transactional
    public PetOwnerDTO update(Integer id, PetOwnerDTO dto) {
        PetOwner petOwner = PetOwnerMapper.toEntity(dto);
        petOwner.setId(id);
        return PetOwnerMapper.toDTO(save(petOwner));
    }

    private PetOwner findById(Integer id) {
        return petOwnerRepository.findById(id)
                .orElseThrow(() -> new PetOwnerNotFoundException(id));
    }

    private PetOwner save(PetOwner petOwner) {
        if (petOwner.getId() != null) {
            findById(petOwner.getId());
        }

        petOwner.setPet(petOwner.getPet().stream().map(pet -> {
            Pet reference = entityManager.getReference(Pet.class, pet.getId());
            reference.setPetOwner(petOwner);
            return reference;
        }).collect(Collectors.toCollection(ArrayList::new)));
        petOwner.setBookingList(petOwner.getBookingList().stream().map(booking -> {
            Booking reference = entityManager.getReference(Booking.class, booking.getId());
            reference.setPetOwnerId(petOwner);
            return reference;
        }).collect(Collectors.toCollection(ArrayList::new)));

        return petOwnerRepository.save(petOwner);
    }

    @Transactional
    public void deleteById(Integer id) {
        PetOwner petOwner = findById(id);
        petOwnerRepository.delete(petOwner);
    }
}
