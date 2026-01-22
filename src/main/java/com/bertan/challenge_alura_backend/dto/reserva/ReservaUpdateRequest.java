package com.bertan.challenge_alura_backend.dto.reserva;

import com.bertan.challenge_alura_backend.domain.Reserva;

import java.time.LocalDateTime;

public record ReservaUpdateRequest(
    Long id,
    Long usuarioId,
    Long salaId,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
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
