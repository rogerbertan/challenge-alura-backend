package com.bertan.challenge_alura_backend.repository;

import com.bertan.challenge_alura_backend.domain.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
