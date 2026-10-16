package com.daniel.application.petzin.dto.mapping;

import com.daniel.application.petzin.dto.BookingDTO;
import com.daniel.application.petzin.entity.Booking;
import com.daniel.application.petzin.entity.Pet;
import com.daniel.application.petzin.entity.PetOwner;

public final class BookingMapper {
    private BookingMapper() {}

    public static BookingDTO toDTO(Booking booking) {
        return new BookingDTO(booking.getId(),
                booking.getPetOwnerId() == null ? null : booking.getPetOwnerId().getId(),
                booking.getPet() == null ? null : booking.getPet().getId(),
                booking.getPetCheckinDate(), booking.getPetCheckoutDate(), booking.getBookingCost(), booking.getKannel());
    }

    public static Booking toEntity(BookingDTO dto) {
        PetOwner owner = null;
        if (dto.petOwnerId() != null) {
            owner = new PetOwner();
            owner.setId(dto.petOwnerId());
        }
        Pet pet = null;
        if (dto.petId() != null) {
            pet = new Pet();
            pet.setId(dto.petId());
        }
        return new Booking(dto.id(), owner, pet, dto.petCheckinDate(),
                dto.petCheckoutDate(), dto.bookingCost(), dto.kannel());
    }
}
