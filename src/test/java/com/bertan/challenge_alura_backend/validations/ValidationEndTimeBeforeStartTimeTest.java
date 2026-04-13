package com.bertan.challenge_alura_backend.validations;

import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.exception.ValidationReservaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidationEndTimeBeforeStartTimeTest {

    private ValidationEndTimeBeforeStartTime validation;

    @BeforeEach
    void setUp() {
        validation = new ValidationEndTimeBeforeStartTime();
    }

    @Test
    void shouldThrowValidationException_whenEndTimeBeforeStartTime() {
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 20, 14, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 1, 20, 10, 0);
        ReservaRequest request = new ReservaRequest(1L, 1L, inicio, fim, 5);

        assertThatThrownBy(() -> validation.validate(request))
                .isInstanceOf(ValidationReservaException.class)
                .hasMessage("Horário de fim deve ser maior que o horário de início");
    }

    @Test
    void shouldThrowValidationException_whenEndTimeEqualsStartTime() {
        LocalDateTime dataHora = LocalDateTime.of(2025, 1, 20, 10, 0);
        ReservaRequest request = new ReservaRequest(1L, 1L, dataHora, dataHora, 5);

        assertThatThrownBy(() -> validation.validate(request))
                .isInstanceOf(ValidationReservaException.class)
                .hasMessage("Horário de fim deve ser maior que o horário de início");
    }

    @Test
    void shouldNotThrowException_whenEndTimeAfterStartTime() {
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 20, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 1, 20, 12, 0);
        ReservaRequest request = new ReservaRequest(1L, 1L, inicio, fim, 5);

        assertThatCode(() -> validation.validate(request))
                .doesNotThrowAnyException();
    }
}