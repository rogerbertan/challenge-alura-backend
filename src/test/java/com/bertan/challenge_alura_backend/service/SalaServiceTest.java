package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.dto.sala.SalaRequest;
import com.bertan.challenge_alura_backend.dto.sala.SalaResponse;
import com.bertan.challenge_alura_backend.dto.sala.SalaUpdateRequest;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala(1L, "Sala de Reunioes", 10);
    }

    @Test
    void shouldReturnListOfRooms_whenRoomsExist() {
        when(salaRepository.findAll()).thenReturn(List.of(sala));

        List<SalaResponse> result = salaService.listarSalas();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).nome()).isEqualTo("Sala de Reunioes");
        assertThat(result.get(0).capacidade()).isEqualTo(10);
        verify(salaRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoRoomsExist() {
        when(salaRepository.findAll()).thenReturn(Collections.emptyList());

        List<SalaResponse> result = salaService.listarSalas();

        assertThat(result).isEmpty();
        verify(salaRepository).findAll();
    }

    @Test
    void shouldReturnRoom_whenRoomExists() {
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));

        SalaResponse result = salaService.buscarSalaPorId(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nome()).isEqualTo("Sala de Reunioes");
        assertThat(result.capacidade()).isEqualTo(10);
        verify(salaRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenRoomNotFound() {
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> salaService.buscarSalaPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).findById(99L);
    }

    @Test
    void shouldSaveRoom_whenValidData() {
        SalaRequest request = new SalaRequest("Nova Sala", 20);

        salaService.criarSala(request);

        verify(salaRepository).save(any(Sala.class));
    }

    @Test
    void shouldUpdateRoom_whenRoomExists() {
        SalaUpdateRequest request = new SalaUpdateRequest(1L, "Sala Atualizada", 15);
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));

        salaService.atualizarSala(request);

        verify(salaRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUpdatingNonExistentRoom() {
        SalaUpdateRequest request = new SalaUpdateRequest(99L, "Sala Inexistente", 10);
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> salaService.atualizarSala(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).findById(99L);
    }

    @Test
    void shouldDeleteRoom_whenRoomExists() {
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));

        salaService.deletarSala(1L);

        verify(salaRepository).findById(1L);
        verify(salaRepository).delete(sala);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenDeletingNonExistentRoom() {
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> salaService.deletarSala(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).findById(99L);
        verify(salaRepository, never()).delete(any());
    }
}