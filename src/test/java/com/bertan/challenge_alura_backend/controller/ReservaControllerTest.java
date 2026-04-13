package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.domain.StatusReserva;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaResponse;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaUpdateRequest;
import com.bertan.challenge_alura_backend.exception.ValidationReservaException;
import com.bertan.challenge_alura_backend.service.ReservaService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
@AutoConfigureJsonTesters
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService reservaService;

    @Autowired
    private JacksonTester<ReservaRequest> reservaRequestJson;

    @Autowired
    private JacksonTester<ReservaUpdateRequest> reservaUpdateRequestJson;

    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @BeforeEach
    void setUp() {
        dataHoraInicio = LocalDateTime.of(2025, 1, 20, 10, 0);
        dataHoraFim = LocalDateTime.of(2025, 1, 20, 12, 0);
    }

    @Test
    void shouldReturnPageOfReservations_whenReservationsExist() throws Exception {
        ReservaResponse response = new ReservaResponse(1L, 1L, 1L, dataHoraInicio, dataHoraFim, StatusReserva.ATIVA, 5);
        Page<ReservaResponse> page = new PageImpl<>(List.of(response));
        when(reservaService.listarReservas(0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/v1/reservas")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].statusReserva").value("ATIVA"));

        verify(reservaService).listarReservas(0, 10);
    }

    @Test
    void shouldReturnReservation_whenReservationExists() throws Exception {
        ReservaResponse response = new ReservaResponse(1L, 1L, 1L, dataHoraInicio, dataHoraFim, StatusReserva.ATIVA, 5);
        when(reservaService.obterReservaPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.statusReserva").value("ATIVA"));

        verify(reservaService).obterReservaPorId(1L);
    }

    @Test
    void shouldReturn404_whenReservationNotFound() throws Exception {
        when(reservaService.obterReservaPorId(99L)).thenThrow(new EntityNotFoundException("Reserva não encontrada"));

        mockMvc.perform(get("/api/v1/reservas/99"))
                .andExpect(status().isNotFound());

        verify(reservaService).obterReservaPorId(99L);
    }

    @Test
    void shouldCreateReservation_whenValidData() throws Exception {
        ReservaRequest request = new ReservaRequest(1L, 1L, dataHoraInicio, dataHoraFim, 5);
        doNothing().when(reservaService).criarReserva(any(ReservaRequest.class));

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaRequestJson.write(request).getJson()))
                .andExpect(status().isCreated());

        verify(reservaService).criarReserva(any(ReservaRequest.class));
    }

    @Test
    void shouldUpdateReservation_whenReservationExists() throws Exception {
        ReservaUpdateRequest request = new ReservaUpdateRequest(1L, 1L, 1L, dataHoraInicio, dataHoraFim, 5);
        doNothing().when(reservaService).atualizarReserva(any(ReservaUpdateRequest.class));

        mockMvc.perform(put("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaUpdateRequestJson.write(request).getJson()))
                .andExpect(status().isNoContent());

        verify(reservaService).atualizarReserva(any(ReservaUpdateRequest.class));
    }

    @Test
    void shouldCancelReservation_whenReservationExists() throws Exception {
        doNothing().when(reservaService).cancelarReserva(1L);

        mockMvc.perform(delete("/api/v1/reservas/cancelar/1"))
                .andExpect(status().isNoContent());

        verify(reservaService).cancelarReserva(1L);
    }

    @Test
    void shouldReturn400WithValidationMessage_whenValidationReservaExceptionThrown() throws Exception {
        ReservaRequest request = new ReservaRequest(1L, 1L, dataHoraInicio, dataHoraFim, 5);
        doThrow(new ValidationReservaException("Sala já ocupada neste horário"))
                .when(reservaService).criarReserva(any(ReservaRequest.class));

        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservaRequestJson.write(request).getJson()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Sala já ocupada neste horário"));
    }

    @Test
    void shouldReturn400WithFieldErrors_whenRequestBodyIsInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"usuarioId\": null, \"salaId\": null}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(":")));
    }

    @Test
    void shouldReturn500_whenUnexpectedExceptionThrown() throws Exception {
        when(reservaService.obterReservaPorId(1L)).thenThrow(new RuntimeException("erro inesperado"));

        mockMvc.perform(get("/api/v1/reservas/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Erro interno no servidor"));
    }
}