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
import com.daniel.application.petiz.dto.mapping.BookingMapper;
import com.daniel.application.petiz.entity.Booking;
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
        return ResponseEntity.ok(bookingService.findAll().stream().map(BookingMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(BookingMapper.toDTO(bookingService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> create(@Valid @RequestBody BookingDTO dto) {
        Booking booking = BookingMapper.toEntity(dto);
        booking.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookingMapper.toDTO(bookingService.save(booking)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody BookingDTO dto) {
        Booking booking = BookingMapper.toEntity(dto);
        booking.setId(id);
        return ResponseEntity.ok(BookingMapper.toDTO(bookingService.save(booking)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
