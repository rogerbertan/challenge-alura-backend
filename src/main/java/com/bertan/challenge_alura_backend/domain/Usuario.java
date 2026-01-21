package com.bertan.challenge_alura_backend.domain;

import com.bertan.challenge_alura_backend.dto.UsuarioRequest;
import com.bertan.challenge_alura_backend.dto.UsuarioUpdateRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    public Usuario() {}

    public Usuario(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Usuario(UsuarioRequest dto) {
        this.nome = dto.nome();
        this.email = dto.email();
    }

    public void atualizarInformacoes(UsuarioUpdateRequest dto) {
        this.nome = dto.nome();
        this.email = dto.email();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
