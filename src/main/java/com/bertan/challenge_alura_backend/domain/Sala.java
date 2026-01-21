package com.bertan.challenge_alura_backend.domain;

public class Sala {

    private Long id;
    private String nome;
    private Integer capacidade;

    public Sala() {}

    public Sala(Long id, String nome, Integer capacidade) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
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
