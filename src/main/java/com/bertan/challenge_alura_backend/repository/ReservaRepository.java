package com.bertan.challenge_alura_backend.repository;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Reserva r
            WHERE r.sala.id = :salaId
            AND r.statusReserva = :status
            AND r.dataHoraInicio < :dataHoraFim
            AND r.dataHoraFim > :dataHoraInicio
            """)
    boolean existsBySalaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
            Long salaId, StatusReserva status, LocalDateTime dataHoraFim, LocalDateTime dataHoraInicio);
}
