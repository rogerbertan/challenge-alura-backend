package com.bertan.challenge_alura_backend.repository;

import com.bertan.challenge_alura_backend.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
