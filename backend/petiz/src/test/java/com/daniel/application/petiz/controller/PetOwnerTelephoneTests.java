package com.daniel.application.petiz.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.daniel.application.petiz.controller.exception.GlobalExceptionHandler;
import com.daniel.application.petiz.dto.PetOwnerDTO;
import com.daniel.application.petiz.dto.mapping.PetOwnerMapper;
import com.daniel.application.petiz.service.PetOwnerService;

class PetOwnerTelephoneTests {
    @Test
    void mapperPreservesTelephoneAsText() {
        PetOwnerDTO dto = new PetOwnerDTO(1, "Ana", "092 99999-9999", List.of(), List.of());
        assertEquals(dto.telephoneNumber(), PetOwnerMapper.toEntity(dto).getTelephoneNumber());
        assertEquals(dto.telephoneNumber(), PetOwnerMapper.toDTO(PetOwnerMapper.toEntity(dto)).telephoneNumber());
    }

    private MockMvc controller() {
        PetOwnerService service = mock(PetOwnerService.class);
        when(service.create(any(PetOwnerDTO.class))).thenAnswer(call -> call.getArgument(0));
        return MockMvcBuilders.standaloneSetup(new PetOwnerController(service))
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void apiReceivesAndReturnsFormattedTelephone() throws Exception {
        controller().perform(post("/api/owners").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Ana","telephoneNumber":"092 99999-9999"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.telephoneNumber").value("092 99999-9999"));
    }

    @Test
    void telephoneIsStillRequired() throws Exception {
        controller().perform(post("/api/owners").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Ana","telephoneNumber":""}
                        """))
                .andExpect(status().isBadRequest());
    }
}
