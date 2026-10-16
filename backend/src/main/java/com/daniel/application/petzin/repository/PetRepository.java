package com.daniel.application.petzin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.application.petzin.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
}
