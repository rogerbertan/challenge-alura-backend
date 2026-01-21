package com.bertan.challenge_alura_backend.repository;

import com.bertan.challenge_alura_backend.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
