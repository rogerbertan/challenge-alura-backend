package com.bertan.challenge_alura_backend.domain;

public class Reserva {

    Long id;
    Usuario usuario;
    Sala sala;
    String dataHoraInicio;
    String dataHoraFim;
    Integer numeroPessoas;
    StatusReserva statusReserva;

    public Reserva() {}

    public Reserva(Usuario usuario, Sala sala, String dataHoraInicio, String dataHoraFim, Integer numeroPessoas) {
        this.usuario = usuario;
        this.sala = sala;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.numeroPessoas = numeroPessoas;
        this.statusReserva = StatusReserva.ATIVA;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public String getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(String dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public String getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(String dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    public Integer getNumeroPessoas() {
        return numeroPessoas;
    }

    public void setNumeroPessoas(Integer numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }
}
