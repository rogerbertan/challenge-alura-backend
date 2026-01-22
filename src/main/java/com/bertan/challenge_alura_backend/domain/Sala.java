package com.bertan.challenge_alura_backend.domain;

import com.bertan.challenge_alura_backend.dto.sala.SalaRequest;
import com.bertan.challenge_alura_backend.dto.sala.SalaUpdateRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer capacidade;

    public Sala() {}

    public Sala(Long id, String nome, Integer capacidade) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public Sala(SalaRequest dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
    }

    public void atualizarInformacoes(SalaUpdateRequest dto) {
        this.nome = dto.nome();
        this.capacidade = dto.capacidade();
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

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }
}
