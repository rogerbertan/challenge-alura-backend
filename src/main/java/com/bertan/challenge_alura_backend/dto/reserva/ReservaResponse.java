package com.bertan.challenge_alura_backend.dto.reserva;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.StatusReserva;

import java.time.LocalDateTime;

public record ReservaResponse(
    Long id,
    Long usuarioId,
    Long salaId,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    StatusReserva statusReserva
) {
    public ReservaResponse(Reserva reserva) {
        this(
            reserva.getId(),
            reserva.getUsuario().getId(),
            reserva.getSala().getId(),
            reserva.getDataHoraInicio(),
            reserva.getDataHoraFim(),
            reserva.getStatusReserva()
        );
    }
}
