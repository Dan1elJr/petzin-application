package com.daniel.application.petiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.application.petiz.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
}
