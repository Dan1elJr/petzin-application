package com.daniel.application.petiz.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookingDTO(
        @Positive Integer id,
        @Positive Integer petOwnerId,
        @NotNull @Positive Integer petId,
        @NotNull LocalDate petCheckinDate,
        @NotNull LocalDate petCheckoutDate,
        @DecimalMin("0.00") BigDecimal bookingCost,
        Integer kannel
) {
    @AssertTrue(message = "A data de checkout deve ser posterior à data de checkin.")
    @JsonIgnore
    public boolean isCheckoutAfterCheckin() {
        if (petCheckinDate == null || petCheckoutDate == null) {
            return true;
        }

        return petCheckoutDate.isAfter(petCheckinDate);
    }
}
