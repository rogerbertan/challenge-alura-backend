package com.bertan.challenge_alura_backend.dto.sala;

import com.bertan.challenge_alura_backend.domain.Sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaUpdateRequest(
        @NotNull
        Long id,
        @NotBlank
        String nome,
        @NotNull
        Integer capacidade
) {
    public SalaUpdateRequest (Sala sala) {
        this(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade()
        );
    }
}
