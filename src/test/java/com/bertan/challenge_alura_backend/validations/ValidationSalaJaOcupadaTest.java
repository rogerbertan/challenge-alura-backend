package com.bertan.challenge_alura_backend.validations;

import com.bertan.challenge_alura_backend.domain.StatusReserva;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.exception.ValidationReservaException;
import com.bertan.challenge_alura_backend.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationSalaJaOcupadaTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ValidationSalaJaOcupada validation;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    @BeforeEach
    void setUp() {
        inicio = LocalDateTime.of(2025, 1, 20, 10, 0);
        fim = LocalDateTime.of(2025, 1, 20, 12, 0);
    }

    @Test
    @DisplayName("Should throw ValidationReservaException when room is already occupied")
    void shouldThrowValidationException_whenRoomIsOccupied() {
        ReservaRequest request = new ReservaRequest(1L, 1L, inicio, fim, 5);
        when(reservaRepository.existsBySalaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                1L, StatusReserva.ATIVA, fim, inicio)).thenReturn(true);

        assertThatThrownBy(() -> validation.validate(request))
                .isInstanceOf(ValidationReservaException.class)
                .hasMessage("Sala já está ocupada nesse período");
    }

    @Test
    @DisplayName("Should not throw exception when room is available")
    void shouldNotThrowException_whenRoomIsAvailable() {
        ReservaRequest request = new ReservaRequest(1L, 1L, inicio, fim, 5);
        when(reservaRepository.existsBySalaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                1L, StatusReserva.ATIVA, fim, inicio)).thenReturn(false);

        assertThatCode(() -> validation.validate(request))
                .doesNotThrowAnyException();
    }
}