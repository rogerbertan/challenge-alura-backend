package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.domain.StatusReserva;
import com.bertan.challenge_alura_backend.domain.Usuario;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaResponse;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaUpdateRequest;
import com.bertan.challenge_alura_backend.repository.ReservaRepository;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import com.bertan.challenge_alura_backend.repository.UsuarioRepository;
import com.bertan.challenge_alura_backend.validations.ValidationReservaRequest;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private ValidationReservaRequest validation;

    private ReservaService reservaService;

    private Usuario usuario;
    private Sala sala;
    private Reserva reserva;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @BeforeEach
    void setUp() {
        List<ValidationReservaRequest> validations = List.of(validation);
        reservaService = new ReservaService(reservaRepository, usuarioRepository, salaRepository, validations);

        usuario = new Usuario(1L, "Joao Silva", "joao@email.com");
        sala = new Sala(1L, "Sala de Reunioes", 10);
        dataHoraInicio = LocalDateTime.of(2025, 1, 20, 10, 0);
        dataHoraFim = LocalDateTime.of(2025, 1, 20, 12, 0);
        reserva = new Reserva(usuario, sala, dataHoraInicio, dataHoraFim, 5);
    }

    @Test
    void shouldReturnPageOfReservations_whenReservationsExist() {
        Page<Reserva> pageReserva = new PageImpl<>(List.of(reserva));
        when(reservaRepository.findAll(any(Pageable.class))).thenReturn(pageReserva);

        Page<ReservaResponse> result = reservaService.listarReservas(0, 10);

        assertThat(result.getContent()).hasSize(1);
        verify(reservaRepository).findAll(any(Pageable.class));
    }

    @Test
    void shouldReturnEmptyPage_whenNoReservationsExist() {
        Page<Reserva> emptyPage = new PageImpl<>(Collections.emptyList());
        when(reservaRepository.findAll(any(Pageable.class))).thenReturn(emptyPage);

        Page<ReservaResponse> result = reservaService.listarReservas(0, 10);

        assertThat(result.getContent()).isEmpty();
        verify(reservaRepository).findAll(any(Pageable.class));
    }

    @Test
    void shouldReturnReservation_whenReservationExists() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        ReservaResponse result = reservaService.obterReservaPorId(1L);

        assertThat(result.usuarioId()).isEqualTo(1L);
        assertThat(result.salaId()).isEqualTo(1L);
        assertThat(result.statusReserva()).isEqualTo(StatusReserva.ATIVA);
        verify(reservaRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenReservationNotFound() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.obterReservaPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Reserva não encontrada");
        verify(reservaRepository).findById(99L);
    }

    @Test
    void shouldSaveReservation_whenValidData() {
        ReservaRequest request = new ReservaRequest(1L, 1L, dataHoraInicio, dataHoraFim, 5);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        doNothing().when(validation).validate(request);

        reservaService.criarReserva(request);

        verify(usuarioRepository).findById(1L);
        verify(salaRepository).findById(1L);
        verify(validation).validate(request);
        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUserNotFound() {
        ReservaRequest request = new ReservaRequest(99L, 1L, dataHoraInicio, dataHoraFim, 5);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.criarReserva(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado");
        verify(usuarioRepository).findById(99L);
        verify(salaRepository, never()).findById(any());
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void shouldThrowEntityNotFoundException_whenRoomNotFound() {
        ReservaRequest request = new ReservaRequest(1L, 99L, dataHoraInicio, dataHoraFim, 5);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.criarReserva(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(usuarioRepository).findById(1L);
        verify(salaRepository).findById(99L);
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void shouldExecuteAllValidations_whenCreatingReservation() {
        ReservaRequest request = new ReservaRequest(1L, 1L, dataHoraInicio, dataHoraFim, 5);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        doNothing().when(validation).validate(request);

        reservaService.criarReserva(request);

        verify(validation).validate(request);
    }

    @Test
    void shouldUpdateReservation_whenReservationExists() {
        ReservaUpdateRequest request = new ReservaUpdateRequest(1L, 1L, 1L, dataHoraInicio, dataHoraFim, 5);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(validation).validate(any(ReservaRequest.class));

        reservaService.atualizarReserva(request);

        verify(reservaRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUpdatingNonExistentReservation() {
        ReservaUpdateRequest request = new ReservaUpdateRequest(99L, 1L, 1L, dataHoraInicio, dataHoraFim, 5);
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.atualizarReserva(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Reserva não encontrada");
        verify(reservaRepository).findById(99L);
    }

    @Test
    void shouldCancelReservation_whenReservationExists() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        reservaService.cancelarReserva(1L);

        assertThat(reserva.getStatusReserva()).isEqualTo(StatusReserva.CANCELADA);
        verify(reservaRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenCancellingNonExistentReservation() {
        when(reservaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.cancelarReserva(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Reserva não encontrada");
        verify(reservaRepository).findById(99L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUpdatingSalaNotFound() {
        ReservaUpdateRequest request = new ReservaUpdateRequest(1L, 1L, 99L, dataHoraInicio, dataHoraFim, 5);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        doNothing().when(validation).validate(any(ReservaRequest.class));
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.atualizarReserva(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).findById(99L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUpdatingUsuarioNotFound() {
        ReservaUpdateRequest request = new ReservaUpdateRequest(1L, 99L, 1L, dataHoraInicio, dataHoraFim, 5);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        doNothing().when(validation).validate(any(ReservaRequest.class));
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reservaService.atualizarReserva(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado");
        verify(usuarioRepository).findById(99L);
    }
}