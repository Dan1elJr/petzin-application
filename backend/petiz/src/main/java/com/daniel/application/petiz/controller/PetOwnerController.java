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

import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.service.PetOwnerService;

@RestController
@RequestMapping("/pet-owners")
public class PetOwnerController {

    private final PetOwnerService petOwnerService;

    @Autowired
    public PetOwnerController(PetOwnerService petOwnerService) {
        this.petOwnerService = petOwnerService;
    }

    @GetMapping
    public ResponseEntity<List<PetOwner>> findAll() {
        return ResponseEntity.ok(petOwnerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetOwner> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(petOwnerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PetOwner> create(@RequestBody PetOwner petOwner) {
        petOwner.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(petOwnerService.save(petOwner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetOwner> update(@PathVariable("id") Integer id, @RequestBody PetOwner petOwner) {
        petOwner.setId(id);
        return ResponseEntity.ok(petOwnerService.save(petOwner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        petOwnerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
