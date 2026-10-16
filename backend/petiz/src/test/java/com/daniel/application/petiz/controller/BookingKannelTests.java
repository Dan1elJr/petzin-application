package com.daniel.application.petiz.controller;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.daniel.application.petiz.controller.exception.GlobalExceptionHandler;
import com.daniel.application.petiz.dto.mapping.BookingMapper;
import com.daniel.application.petiz.entity.Booking;
import com.daniel.application.petiz.entity.Pet;
import com.daniel.application.petiz.entity.PetOwner;
import com.daniel.application.petiz.repository.BookingRepository;
import com.daniel.application.petiz.service.BookingService;

class BookingKannelTests {
    private final BookingRepository repository = mock(BookingRepository.class);
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new BookingController(new BookingService(repository)))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        when(repository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            if (booking.getId() == null) booking.setId(7);
            return booking;
        });
    }

    private Booking booking(Integer kannel) {
        PetOwner owner = new PetOwner();
        owner.setId(1);
        Pet pet = new Pet();
        pet.setId(2);
        return new Booking(7, owner, pet, LocalDate.of(2026, 10, 15),
                LocalDate.of(2026, 10, 16), new BigDecimal("50.00"), kannel);
    }

    private String payload(String kannel) {
        return """
                {"petOwnerId":1,"petId":2,"petCheckinDate":"2026-10-15",
                 "petCheckoutDate":"2026-10-16","bookingCost":50.00,"kannel":%s}
                """.formatted(kannel);
    }

    @Test
    void createPersistsAndReturnsKannel() throws Exception {
        mvc.perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON).content(payload("3")))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.kannel").value(3));
        ArgumentCaptor<Booking> saved = ArgumentCaptor.forClass(Booking.class);
        verify(repository).save(saved.capture());
        org.junit.jupiter.api.Assertions.assertEquals(3, saved.getValue().getKannel());
    }

    @Test
    void updatePersistsChangedKannel() throws Exception {
        when(repository.findById(7)).thenReturn(Optional.of(booking(3)));
        mvc.perform(put("/api/bookings/7").contentType(MediaType.APPLICATION_JSON).content(payload("8")))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.kannel").value(8));
        ArgumentCaptor<Booking> saved = ArgumentCaptor.forClass(Booking.class);
        verify(repository).save(saved.capture());
        org.junit.jupiter.api.Assertions.assertEquals(8, saved.getValue().getKannel());
    }

    @Test
    void findByIdReturnsKannel() throws Exception {
        when(repository.findById(7)).thenReturn(Optional.of(booking(4)));
        mvc.perform(get("/api/bookings/7")).andExpect(status().isOk())
                .andExpect(jsonPath("$.kannel").value(4));
    }

    @Test
    void findAllReturnsKannel() throws Exception {
        when(repository.findAll()).thenReturn(List.of(booking(5)));
        mvc.perform(get("/api/bookings")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].kannel").value(5));
    }

    @Test
    void kannelRemainsOptionalLikeEntity() throws Exception {
        assertNull(BookingMapper.toEntity(BookingMapper.toDTO(booking(null))).getKannel());
        mvc.perform(post("/api/bookings").contentType(MediaType.APPLICATION_JSON).content(payload("null")))
                .andExpect(status().isCreated());
    }
}
