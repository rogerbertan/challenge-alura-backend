package com.bertan.challenge_alura_backend.dto;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.Usuario;

import java.time.LocalDateTime;

public record ReservaRequest(
    Long usuarioId,
    Long salaId,
    LocalDateTime dataHoraInicio,
    LocalDateTime dataHoraFim,
    Integer numeroPessoas
) {
    public ReservaRequest(Reserva reserva) {
        this(
            reserva.getUsuario().getId(),
            reserva.getSala().getId(),
            reserva.getDataHoraInicio(),
            reserva.getDataHoraFim(),
            reserva.getNumeroPessoas()
        );
    }
}
