package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.dto.SalaResponse;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
