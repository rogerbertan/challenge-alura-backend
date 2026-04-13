package com.bertan.challenge_alura_backend.dto.sala;

import com.bertan.challenge_alura_backend.domain.Sala;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequest(
    @NotBlank
    String nome,
    @NotNull
    @Min(value = 1, message = "A capacidade precisa ser de pelo menos 1")
    Integer capacidade
) {
    public SalaRequest(Sala sala) {
        this(
            sala.getNome(),
            sala.getCapacidade()
        );
    }
}
