package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.dto.sala.SalaRequest;
import com.bertan.challenge_alura_backend.dto.sala.SalaResponse;
import com.bertan.challenge_alura_backend.dto.sala.SalaUpdateRequest;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Should return list of rooms when rooms exist")
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
    @DisplayName("Should return empty list when no rooms exist")
    void shouldReturnEmptyList_whenNoRoomsExist() {
        when(salaRepository.findAll()).thenReturn(Collections.emptyList());

        List<SalaResponse> result = salaService.listarSalas();

        assertThat(result).isEmpty();
        verify(salaRepository).findAll();
    }

    @Test
    @DisplayName("Should return room when room exists")
    void shouldReturnRoom_whenRoomExists() {
        when(salaRepository.findById(1L)).thenReturn(Optional.of(sala));

        SalaResponse result = salaService.buscarSalaPorId(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nome()).isEqualTo("Sala de Reunioes");
        assertThat(result.capacidade()).isEqualTo(10);
        verify(salaRepository).findById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when room not found")
    void shouldThrowEntityNotFoundException_whenRoomNotFound() {
        when(salaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> salaService.buscarSalaPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).findById(99L);
    }

    @Test
    @DisplayName("Should save room when valid data")
    void shouldSaveRoom_whenValidData() {
        SalaRequest request = new SalaRequest("Nova Sala", 20);

        salaService.criarSala(request);

        verify(salaRepository).save(any(Sala.class));
    }

    @Test
    @DisplayName("Should update room when room exists")
    void shouldUpdateRoom_whenRoomExists() {
        SalaUpdateRequest request = new SalaUpdateRequest(1L, "Sala Atualizada", 15);
        when(salaRepository.existsById(1L)).thenReturn(true);
        when(salaRepository.getReferenceById(1L)).thenReturn(sala);

        salaService.atualizarSala(request);

        verify(salaRepository).existsById(1L);
        verify(salaRepository).getReferenceById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when updating non-existent room")
    void shouldThrowEntityNotFoundException_whenUpdatingNonExistentRoom() {
        SalaUpdateRequest request = new SalaUpdateRequest(99L, "Sala Inexistente", 10);
        when(salaRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> salaService.atualizarSala(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).existsById(99L);
        verify(salaRepository, never()).getReferenceById(any());
    }

    @Test
    @DisplayName("Should delete room when room exists")
    void shouldDeleteRoom_whenRoomExists() {
        when(salaRepository.existsById(1L)).thenReturn(true);

        salaService.deletarSala(1L);

        verify(salaRepository).existsById(1L);
        verify(salaRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw EntityNotFoundException when deleting non-existent room")
    void shouldThrowEntityNotFoundException_whenDeletingNonExistentRoom() {
        when(salaRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> salaService.deletarSala(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Sala não encontrada");
        verify(salaRepository).existsById(99L);
        verify(salaRepository, never()).deleteById(any());
    }
}