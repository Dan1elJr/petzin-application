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

import com.daniel.application.petiz.dto.BookingDTO;
import com.daniel.application.petiz.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> findAll() {
        return ResponseEntity.ok(bookingService.findAllDTO());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(bookingService.findByIdDTO(id));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(@Valid @RequestBody BookingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody BookingDTO dto) {
        return ResponseEntity.ok(bookingService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
