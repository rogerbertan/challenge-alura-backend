package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.dto.SalaResponse;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<SalaResponse> listarSalas() {

        return salaRepository.findAll()
                .stream()
                .map(SalaResponse::new)
                .toList();
    }

    public SalaResponse buscarSalaPorId(Long id) {

        return salaRepository.findById(id)
                .map(SalaResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));
    }
}
