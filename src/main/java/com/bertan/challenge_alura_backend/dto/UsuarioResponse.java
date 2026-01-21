package com.bertan.challenge_alura_backend.dto;

import com.bertan.challenge_alura_backend.domain.Usuario;

public record UsuarioResponse(
    Long id,
    String nome,
    String email
) {
    public UsuarioResponse(Usuario usuario) {
        this(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}
