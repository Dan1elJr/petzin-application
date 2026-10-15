package com.daniel.application.petiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.application.petiz.entity.PetOwner;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {

}
