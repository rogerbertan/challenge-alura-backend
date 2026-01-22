package com.bertan.challenge_alura_backend.dto.sala;

import com.bertan.challenge_alura_backend.domain.Sala;

public record SalaResponse(
        Long id,
        String nome,
        Integer capacidade
) {


    public SalaResponse(Sala sala) {
        this(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade()
        );
    }
}
