package com.daniel.application.petiz.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.repository.BookingRepository;
import com.daniel.application.petiz.repository.PetOwnerRepository;
import com.daniel.application.petiz.repository.PetRepository;

import jakarta.persistence.EntityNotFoundException;

class ServiceTests {

    private final BookingRepository bookings = mock(BookingRepository.class);
    private final PetOwnerRepository owners = mock(PetOwnerRepository.class);
    private final PetRepository pets = mock(PetRepository.class);
    private final BookingService bookingService = new BookingService(bookings);
    private final PetOwnerService ownerService = new PetOwnerService(owners);
    private final PetService petService = new PetService(pets);

    @Test
    void missingBookingsCannotBeReadUpdatedOrDeleted() {
        when(bookings.findById(1)).thenReturn(Optional.empty());
        Booking booking = new Booking();
        booking.setId(1);

        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> bookingService.findById(1)),
                () -> assertThrows(EntityNotFoundException.class, () -> bookingService.save(booking)),
                () -> assertThrows(EntityNotFoundException.class, () -> bookingService.deleteById(1)));
        verify(bookings, never()).save(any());
        verify(bookings, never()).delete(any(Booking.class));
    }

    @Test
    void missingOwnersCannotBeReadUpdatedOrDeleted() {
        when(owners.findById(1)).thenReturn(Optional.empty());
        PetOwner owner = new PetOwner();
        owner.setId(1);

        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> ownerService.findById(1)),
                () -> assertThrows(EntityNotFoundException.class, () -> ownerService.save(owner)),
                () -> assertThrows(EntityNotFoundException.class, () -> ownerService.deleteById(1)));
        verify(owners, never()).save(any());
        verify(owners, never()).delete(any(PetOwner.class));
    }

    @Test
    void missingPetsCannotBeReadUpdatedOrDeleted() {
        when(pets.findById(1)).thenReturn(Optional.empty());
        Pet pet = new Pet();
        pet.setId(1);

        assertAll(
                () -> assertThrows(EntityNotFoundException.class, () -> petService.findById(1)),
                () -> assertThrows(EntityNotFoundException.class, () -> petService.save(pet)),
                () -> assertThrows(EntityNotFoundException.class, () -> petService.deleteById(1)));
        verify(pets, never()).save(any());
        verify(pets, never()).delete(any(Pet.class));
    }

    @Test
    void nullEntitiesAreRejected() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> bookingService.save(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> ownerService.save(null)),
                () -> assertThrows(IllegalArgumentException.class, () -> petService.save(null)));
    }

    @Test
    void newEntitiesCanBeSaved() {
        Booking booking = new Booking();
        PetOwner owner = new PetOwner();
        Pet pet = new Pet();

        bookingService.save(booking);
        ownerService.save(owner);
        petService.save(pet);

        verify(bookings).save(booking);
        verify(owners).save(owner);
        verify(pets).save(pet);
    }
}
