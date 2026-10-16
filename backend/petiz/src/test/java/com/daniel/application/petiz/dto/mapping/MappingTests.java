package com.daniel.application.petiz.dto.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.PetOwner;

import tools.jackson.databind.json.JsonMapper;

class MappingTests {
    @Test
    void circularEntityRelationshipsBecomeIdsInJson() throws Exception {
        PetOwner owner = new PetOwner();
        owner.setId(1);
        Pet pet = new Pet(2, "Rex", "Labrador", owner);
        Booking booking = new Booking(3, owner, pet, null, null, null);
        owner.getPet().add(pet);
        owner.getBookingList().add(booking);

        var mapper = JsonMapper.builder().build();
        var petJson = mapper.readTree(mapper.writeValueAsString(PetMapper.toDTO(pet)));
        assertEquals(1, petJson.get("petOwnerId").asInt());
        assertFalse(petJson.has("petOwner"));

        var ownerJson = mapper.readTree(mapper.writeValueAsString(PetOwnerMapper.toDTO(owner)));
        assertEquals(2, ownerJson.get("petIds").get(0).asInt());
        assertEquals(3, ownerJson.get("bookingIds").get(0).asInt());
        assertFalse(ownerJson.has("pet"));

        var bookingJson = mapper.readTree(mapper.writeValueAsString(BookingMapper.toDTO(booking)));
        assertEquals(2, bookingJson.get("petId").asInt());
        assertFalse(bookingJson.has("pet"));

        assertEquals(PetMapper.toDTO(pet), PetMapper.toDTO(PetMapper.toEntity(PetMapper.toDTO(pet))));
        assertEquals(BookingMapper.toDTO(booking),
                BookingMapper.toDTO(BookingMapper.toEntity(BookingMapper.toDTO(booking))));
        var mappedOwner = PetOwnerMapper.toEntity(PetOwnerMapper.toDTO(owner));
        assertEquals(List.of(2), PetOwnerMapper.toDTO(mappedOwner).petIds());
        assertEquals(List.of(3), PetOwnerMapper.toDTO(mappedOwner).bookingIds());
    }
}
