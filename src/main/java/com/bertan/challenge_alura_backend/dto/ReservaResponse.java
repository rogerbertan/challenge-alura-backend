package com.bertan.challenge_alura_backend.dto;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.domain.StatusReserva;
import com.bertan.challenge_alura_backend.domain.Usuario;

import java.time.LocalDateTime;

public record ReservaResponse(
    Long id,
    Usuario usuario,
    Sala sala,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    StatusReserva statusReserva
) {
    public ReservaResponse(Reserva reserva) {
        this(
            reserva.getId(),
            reserva.getUsuario(),
            reserva.getSala(),
            reserva.getDataHoraInicio(),
            reserva.getDataHoraFim(),
            reserva.getStatusReserva()
        );
    }
}
