package com.daniel.application.petzin.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.daniel.application.petzin.entity.Booking;
import com.daniel.application.petzin.repository.BookingRepository;

class BookingServiceTests {

    @Test
    void shouldDeleteExistingBooking() {
        // Arrange
        BookingRepository repository = mock(BookingRepository.class);
        BookingService service = new BookingService(repository);
        Booking booking = new Booking();
        when(repository.findById(1)).thenReturn(Optional.of(booking));

        // Act
        service.deleteById(1);

        // Assert
        verify(repository).delete(booking);
    }
}
