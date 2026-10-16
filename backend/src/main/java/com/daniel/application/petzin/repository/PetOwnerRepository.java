package com.daniel.application.petzin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.application.petzin.entity.PetOwner;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Integer> {

}
