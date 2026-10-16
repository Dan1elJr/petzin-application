package com.daniel.application.petiz.dto.mapping;

import java.util.ArrayList;
import java.util.List;

import com.daniel.application.petiz.dto.PetOwnerDTO;
import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.PetOwner;

public final class PetOwnerMapper {
    private PetOwnerMapper() {}

    public static PetOwnerDTO toDTO(PetOwner owner) {
        return new PetOwnerDTO(owner.getId(), owner.getName(), owner.getTelephoneNumber(),
                owner.getPet().stream().map(Pet::getId).toList(),
                owner.getBookingList().stream().map(Booking::getId).toList());
    }

    public static PetOwner toEntity(PetOwnerDTO dto) {
        List<Pet> pets = new ArrayList<>();
        if (dto.petIds() != null) {
            for (Integer id : dto.petIds()) {
                Pet pet = new Pet();
                pet.setId(id);
                pets.add(pet);
            }
        }
        List<Booking> bookings = new ArrayList<>();
        if (dto.bookingIds() != null) {
            for (Integer id : dto.bookingIds()) {
                Booking booking = new Booking();
                booking.setId(id);
                bookings.add(booking);
            }
        }
        return new PetOwner(dto.id(), dto.name(), dto.telephoneNumber(), pets, bookings);
    }
}
