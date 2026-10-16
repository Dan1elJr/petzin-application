package com.daniel.application.petzin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.application.petzin.dto.PetOwnerDTO;
import com.daniel.application.petzin.service.PetOwnerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/owners")
public class PetOwnerController {

    private final PetOwnerService petOwnerService;

    @Autowired
    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping
    public ResponseEntity<List<PetOwnerDTO>> findAll() {
        return ResponseEntity.ok(petOwnerService.findAllDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetOwnerDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(petOwnerService.findByIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<PetOwnerDTO> create(@Valid @RequestBody PetOwnerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petOwnerService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetOwnerDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody PetOwnerDTO dto) {
        return ResponseEntity.ok(petOwnerService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        petOwnerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
