package com.bertan.challenge_alura_backend.dto;

import com.bertan.challenge_alura_backend.domain.Sala;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequest(
    @NotBlank
    String nome,
    @NotNull
    Integer capacidade
) {
    public SalaRequest(Sala sala) {
        this(
            sala.getNome(),
            sala.getCapacidade()
        );
    }
}
