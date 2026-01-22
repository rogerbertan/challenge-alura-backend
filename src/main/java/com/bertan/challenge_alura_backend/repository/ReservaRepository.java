package com.bertan.challenge_alura_backend.repository;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    boolean existsBySalaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            Long salaId, StatusReserva status, LocalDateTime dataHoraFim, LocalDateTime dataHoraInicio);
}
