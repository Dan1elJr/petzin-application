package com.daniel.application.petiz.controller;

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

import com.daniel.application.petiz.dto.PetDTO;
import com.daniel.application.petiz.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> findAll() {
        return ResponseEntity.ok(petService.findAllDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(petService.findByIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody PetDTO dto) {
        return ResponseEntity.ok(petService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        petService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
