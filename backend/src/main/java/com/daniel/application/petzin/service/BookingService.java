package com.daniel.application.petzin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.application.petzin.dto.BookingDTO;
import com.daniel.application.petzin.dto.mapping.BookingMapper;
import com.daniel.application.petzin.entity.Booking;
import com.daniel.application.petzin.repository.BookingRepository;

import com.daniel.application.petzin.service.exception.BookingNotFoundException;

@Service

public class BookingService {

    private final BookingRepository bookingRepository;
	
    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    
    public List<BookingDTO> findAllDTO() {
        return bookingRepository.findAll().stream().map(BookingMapper::toDTO).toList();
    }

  
    public BookingDTO findByIdDTO(Integer id) {
        return BookingMapper.toDTO(findById(id));
    }

    @Transactional
    public BookingDTO create(BookingDTO dto) {
        Booking booking = BookingMapper.toEntity(dto);
        booking.setId(null);
        return BookingMapper.toDTO(bookingRepository.save(booking));
    }

    @Transactional
    public BookingDTO update(Integer id, BookingDTO dto) {
        findById(id);
        Booking booking = BookingMapper.toEntity(dto);
        booking.setId(id);
        return BookingMapper.toDTO(bookingRepository.save(booking));
    }

    private Booking findById(Integer id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    @Transactional
    public void deleteById(Integer id) {
        Booking booking = findById(id);
        bookingRepository.delete(booking);
    }
}
