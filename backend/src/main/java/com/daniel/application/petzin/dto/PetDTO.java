package com.daniel.application.petzin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PetDTO(
        @Positive Integer id,
        @NotBlank @Size(max = 255) String petName,
        @NotBlank @Size(max = 255) String petBreed,
        @NotNull @Positive Integer petOwnerId
) {
}
