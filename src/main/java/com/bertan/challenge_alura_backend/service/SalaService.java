package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.dto.sala.SalaRequest;
import com.bertan.challenge_alura_backend.dto.sala.SalaResponse;
import com.bertan.challenge_alura_backend.dto.sala.SalaUpdateRequest;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional(readOnly = true)
    public List<SalaResponse> listarSalas() {

        return salaRepository.findAll()
                .stream()
                .map(SalaResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public SalaResponse buscarSalaPorId(Long id) {

        return salaRepository.findById(id)
                .map(SalaResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));
    }

    @Transactional
    public void criarSala(SalaRequest dto) {

        Sala sala = new Sala(dto);
        salaRepository.save(sala);
    }

    @Transactional
    public void atualizarSala(SalaUpdateRequest dto) {

        Sala sala = salaRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));
        sala.atualizarInformacoes(dto);
    }

    @Transactional
    public void deletarSala(Long id) {

        Sala sala = salaRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        salaRepository.delete(sala);
    }
}
