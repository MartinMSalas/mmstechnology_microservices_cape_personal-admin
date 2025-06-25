package com.mmstechnology.personal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmstechnology.personal.dto.PersonalDto;
import com.mmstechnology.personal.service.PersonalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonalController.class)
class PersonalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonalService personalService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllPersonal() throws Exception {
        when(personalService.findAll()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/personal"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePersonal() throws Exception {
        PersonalDto dto = new PersonalDto(
            1L, "20123456789", "12345678", "Juan", "Pérez", "LEG123",
            LocalDate.of(1990, 1, 1), LocalDate.of(2020, 1, 1),
            "admin", Collections.emptyList()
        );

        when(personalService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/personal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuil").value("20123456789"));
    }
}
