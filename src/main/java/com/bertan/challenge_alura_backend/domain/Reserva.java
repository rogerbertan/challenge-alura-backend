package com.bertan.challenge_alura_backend.domain;

import com.bertan.challenge_alura_backend.dto.ReservaUpdateRequest;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    Sala sala;

    LocalDateTime dataHoraInicio;

    LocalDateTime dataHoraFim;

    Integer numeroPessoas;

    @Enumerated(EnumType.STRING)
    StatusReserva statusReserva;

    public Reserva() {}

    public Reserva(Usuario usuario, Sala sala, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Integer numeroPessoas) {
        this.usuario = usuario;
        this.sala = sala;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.numeroPessoas = numeroPessoas;
        this.statusReserva = StatusReserva.ATIVA;
    }

    public void atualizarInformacoes(Usuario usuario, Sala sala, ReservaUpdateRequest dto) {
        this.usuario = usuario;
        this.sala = sala;
        this.dataHoraInicio = dto.dataHoraInicio();
        this.dataHoraFim = dto.dataHoraFim();
        this.numeroPessoas = dto.numeroPessoas();
    }

    public void cancelarReserva() {
        this.statusReserva = StatusReserva.CANCELADA;
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

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(LocalDateTime dataHoraFim) {
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
