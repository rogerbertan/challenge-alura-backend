package com.bertan.challenge_alura_backend.dto.reserva;

import com.bertan.challenge_alura_backend.domain.Reserva;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaUpdateRequest(
    Long id,
    @NotNull
    Long usuarioId,
    @NotNull
    Long salaId,
    @NotNull
    LocalDateTime dataHoraInicio,
    @NotNull
    LocalDateTime dataHoraFim,
    @NotNull
    @Min(value = 1, message = "O número de pessoas deve ser pelo menos 1")
    Integer numeroPessoas
) {
    public ReservaUpdateRequest(Reserva reserva) {
        this(
                reserva.getId(),
            reserva.getUsuario().getId(),
            reserva.getSala().getId(),
            reserva.getDataHoraInicio(),
            reserva.getDataHoraFim(),
            reserva.getNumeroPessoas()
        );
    }
}
