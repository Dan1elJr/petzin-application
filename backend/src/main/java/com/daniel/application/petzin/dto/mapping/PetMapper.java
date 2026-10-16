package com.daniel.application.petzin.dto.mapping;

import com.daniel.application.petzin.dto.PetDTO;
import com.daniel.application.petzin.entity.Pet;
import com.daniel.application.petzin.entity.PetOwner;

public final class PetMapper {
    private PetMapper() {}

    public static PetDTO toDTO(Pet pet) {
        return new PetDTO(pet.getId(), pet.getPetName(), pet.getPetBreed(),
                pet.getPetOwner() == null ? null : pet.getPetOwner().getId());
    }

    public static Pet toEntity(PetDTO dto) {
        PetOwner owner = null;
        if (dto.petOwnerId() != null) {
            owner = new PetOwner();
            owner.setId(dto.petOwnerId());
        }
        return new Pet(dto.id(), dto.petName(), dto.petBreed(), owner);
    }
}
