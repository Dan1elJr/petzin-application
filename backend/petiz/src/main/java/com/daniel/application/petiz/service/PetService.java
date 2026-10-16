package com.daniel.application.petiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.application.petiz.dto.PetDTO;
import com.daniel.application.petiz.dto.mapping.PetMapper;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.repository.PetRepository;

import com.daniel.application.petiz.service.exception.PetNotFoundException;

@Service
public class PetService {

    private final PetRepository petRepository;
	
    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

   
    public List<PetDTO> findAllDTO() {
        return petRepository.findAll().stream().map(PetMapper::toDTO).toList();
    }

   
    public PetDTO findByIdDTO(Integer id) {
        return PetMapper.toDTO(findById(id));
    }

    @Transactional
    public PetDTO create(PetDTO dto) {
        Pet pet = PetMapper.toEntity(dto);
        pet.setId(null);
        return PetMapper.toDTO(save(pet));
    }

    @Transactional
    public PetDTO update(Integer id, PetDTO dto) {
        Pet pet = PetMapper.toEntity(dto);
        pet.setId(id);
        return PetMapper.toDTO(save(pet));
    }

    private Pet findById(Integer id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
    }

    private Pet save(Pet pet) {
  
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
