package com.daniel.application.petiz.controller.exception;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.daniel.application.petiz.controller.BookingController;
import com.daniel.application.petiz.controller.PetController;
import com.daniel.application.petiz.controller.PetOwnerController;
import com.daniel.application.petiz.service.BookingService;
import com.daniel.application.petiz.service.PetOwnerService;
import com.daniel.application.petiz.service.PetService;
import com.daniel.application.petiz.service.exception.BookingNotFoundException;
import com.daniel.application.petiz.service.exception.PetNotFoundException;
import com.daniel.application.petiz.service.exception.PetOwnerNotFoundException;

class GlobalExceptionHandlerTests {
    private final PetService pets = mock(PetService.class);
    private final PetOwnerService owners = mock(PetOwnerService.class);
    private final BookingService bookings = mock(BookingService.class);
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new PetController(pets),
                new PetOwnerController(owners), new BookingController(bookings))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void missingEntitiesReturn404WithSemanticMessages() throws Exception {
        when(pets.findByIdDTO(1)).thenThrow(new PetNotFoundException(1));
        when(owners.findByIdDTO(1)).thenThrow(new PetOwnerNotFoundException(1));
        when(bookings.findByIdDTO(1)).thenThrow(new BookingNotFoundException(1));

        mvc.perform(get("/api/pets/1")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Pet não encontrado: 1"));
        mvc.perform(get("/api/owners/1")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Tutor não encontrado: 1"));
        mvc.perform(get("/api/bookings/1")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Reserva não encontrada: 1"));
    }

    @Test
    void invalidFieldsReturn400WithValidationDetails() throws Exception {
        mvc.perform(post("/api/pets").contentType(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isNotEmpty());
    }

    @Test
    void invalidJsonAndPathIdsReturn400() throws Exception {
        mvc.perform(post("/api/pets").contentType(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/pets/abc")).andExpect(status().isBadRequest());
    }

    @Test
    void databaseConflictsReturn409WithoutDatabaseDetails() throws Exception {
        when(pets.findByIdDTO(1)).thenThrow(new DataIntegrityViolationException("SQL secret"));
        mvc.perform(get("/api/pets/1")).andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail").value(
                        "A operação viola uma restrição dos dados. Verifique os vínculos entre pets, tutores e reservas."));
    }

    @Test
    void unexpectedErrorsReturn500WithoutInternalDetails() throws Exception {
        when(pets.findByIdDTO(1)).thenThrow(new IllegalStateException("Internal secret"));
        mvc.perform(get("/api/pets/1")).andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.detail").value(
                        "Não foi possível concluir a operação devido a um erro interno."));
    }
}
