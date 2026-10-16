package com.daniel.application.petiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.repository.BookingRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
	
    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Booking findById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada: " + id));
    }

    @Transactional
    public Booking save(Booking booking) {
     
        if (booking.getId() != null) {
            findById(booking.getId());
        }

        return bookingRepository.save(booking);
    }

    @Transactional
    public void deleteById(Integer id) {
        Booking booking = findById(id);
        bookingRepository.delete(booking);
    }
}
