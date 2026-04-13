package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.dto.sala.SalaRequest;
import com.bertan.challenge_alura_backend.dto.sala.SalaResponse;
import com.bertan.challenge_alura_backend.dto.sala.SalaUpdateRequest;
import com.bertan.challenge_alura_backend.service.SalaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaController.class)
@AutoConfigureJsonTesters
class SalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalaService salaService;

    @Autowired
    private JacksonTester<SalaRequest> salaRequestJson;

    @Autowired
    private JacksonTester<SalaUpdateRequest> salaUpdateRequestJson;

    @Test
    void shouldReturnListOfRooms_whenRoomsExist() throws Exception {
        SalaResponse response = new SalaResponse(1L, "Sala de Reunioes", 10);
        when(salaService.listarSalas()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/salas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Sala de Reunioes"))
                .andExpect(jsonPath("$[0].capacidade").value(10));

        verify(salaService).listarSalas();
    }

    @Test
    void shouldReturnRoom_whenRoomExists() throws Exception {
        SalaResponse response = new SalaResponse(1L, "Sala de Reunioes", 10);
        when(salaService.buscarSalaPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/salas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Sala de Reunioes"))
                .andExpect(jsonPath("$.capacidade").value(10));

        verify(salaService).buscarSalaPorId(1L);
    }

    @Test
    void shouldReturn404_whenRoomNotFound() throws Exception {
        when(salaService.buscarSalaPorId(99L)).thenThrow(new EntityNotFoundException("Sala não encontrada"));

        mockMvc.perform(get("/api/v1/salas/99"))
                .andExpect(status().isNotFound());

        verify(salaService).buscarSalaPorId(99L);
    }

    @Test
    void shouldCreateRoom_whenValidData() throws Exception {
        SalaRequest request = new SalaRequest("Nova Sala", 20);
        doNothing().when(salaService).criarSala(any(SalaRequest.class));

        mockMvc.perform(post("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(salaRequestJson.write(request).getJson()))
                .andExpect(status().isCreated());

        verify(salaService).criarSala(any(SalaRequest.class));
    }

    @Test
    void shouldUpdateRoom_whenRoomExists() throws Exception {
        SalaUpdateRequest request = new SalaUpdateRequest(1L, "Sala Atualizada", 15);
        doNothing().when(salaService).atualizarSala(any(SalaUpdateRequest.class));

        mockMvc.perform(put("/api/v1/salas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(salaUpdateRequestJson.write(request).getJson()))
                .andExpect(status().isNoContent());

        verify(salaService).atualizarSala(any(SalaUpdateRequest.class));
    }

    @Test
    void shouldDeleteRoom_whenRoomExists() throws Exception {
        doNothing().when(salaService).deletarSala(1L);

        mockMvc.perform(delete("/api/v1/salas/1"))
                .andExpect(status().isNoContent());

        verify(salaService).deletarSala(1L);
    }
}