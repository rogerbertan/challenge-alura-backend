package com.bertan.challenge_alura_backend.dto;

import com.bertan.challenge_alura_backend.domain.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest (
    @NotBlank
    String nome,
    @NotBlank
    @Email
    String email
) {
    public UsuarioRequest(Usuario usuario) {
        this(usuario.getNome(), usuario.getEmail());
    }
}
