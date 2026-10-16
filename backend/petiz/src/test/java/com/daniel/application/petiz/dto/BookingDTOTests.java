package com.daniel.application.petiz.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.constraints.NotNull;

class BookingDTOTests {

    @Test
    void missingDatesProduceNotNullViolationsWithoutThrowing() {
        BookingDTO booking = new BookingDTO(null, null, 1, null, null, null);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var violations = factory.getValidator().validate(booking);
            assertEquals(2, violations.size());
            assertTrue(violations.stream().allMatch(violation ->
                    violation.getConstraintDescriptor().getAnnotation() instanceof NotNull));
        }
    }

    @Test
    void checkoutMustBeAfterCheckin() {
        LocalDate checkin = LocalDate.of(2026, 11, 1);

        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertEquals(1, validator.validate(new BookingDTO(
                    null, null, 1, checkin, checkin.minusDays(1), null)).size());
            assertEquals(1, validator.validate(new BookingDTO(
                    null, null, 1, checkin, checkin, null)).size());
            assertTrue(validator.validate(new BookingDTO(
                    null, null, 1, checkin, checkin.plusDays(1), null)).isEmpty());
        }
    }
}
