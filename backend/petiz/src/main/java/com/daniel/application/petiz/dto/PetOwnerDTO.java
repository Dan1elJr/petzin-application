package com.daniel.application.petiz.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PetOwnerDTO(
        @Positive Integer id,
        @NotBlank @Size(max = 255) String name,
        @NotNull @Positive Integer telephoneNumber,
        List<@NotNull @Positive Integer> petIds,
        List<@NotNull @Positive Integer> bookingIds
) {
}
