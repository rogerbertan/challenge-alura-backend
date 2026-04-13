package com.bertan.challenge_alura_backend.domain;

import com.bertan.challenge_alura_backend.dto.reserva.ReservaUpdateRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservaTest {

    @Test
    void shouldCreateReservaWithNullFields_whenNoArgsConstructorUsed() {
        Reserva reserva = new Reserva();

        assertThat(reserva.getId()).isNull();
        assertThat(reserva.getUsuario()).isNull();
        assertThat(reserva.getSala()).isNull();
        assertThat(reserva.getDataHoraInicio()).isNull();
        assertThat(reserva.getDataHoraFim()).isNull();
        assertThat(reserva.getNumeroPessoas()).isNull();
        assertThat(reserva.getStatusReserva()).isNull();
    }

    @Test
    void shouldUpdateUsuario_whenSetUsuarioCalled() {
        Reserva reserva = new Reserva();
        Usuario usuario = new Usuario(1L, "Joao", "joao@email.com");

        reserva.setUsuario(usuario);

        assertThat(reserva.getUsuario()).isEqualTo(usuario);
    }

    @Test
    void shouldUpdateSala_whenSetSalaCalled() {
        Reserva reserva = new Reserva();
        Sala sala = new Sala(1L, "Sala A", 10);

        reserva.setSala(sala);

        assertThat(reserva.getSala()).isEqualTo(sala);
    }

    @Test
    void shouldUpdateDataHoraInicio_whenSetDataHoraInicioCalled() {
        Reserva reserva = new Reserva();
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 20, 9, 0);

        reserva.setDataHoraInicio(inicio);

        assertThat(reserva.getDataHoraInicio()).isEqualTo(inicio);
    }

    @Test
    void shouldUpdateDataHoraFim_whenSetDataHoraFimCalled() {
        Reserva reserva = new Reserva();
        LocalDateTime fim = LocalDateTime.of(2025, 1, 20, 11, 0);

        reserva.setDataHoraFim(fim);

        assertThat(reserva.getDataHoraFim()).isEqualTo(fim);
    }

    @Test
    void shouldUpdateNumeroPessoas_whenSetNumeroPessoasCalled() {
        Reserva reserva = new Reserva();

        reserva.setNumeroPessoas(8);

        assertThat(reserva.getNumeroPessoas()).isEqualTo(8);
    }

    @Test
    void shouldUpdateStatusReserva_whenSetStatusReservaCalled() {
        Reserva reserva = new Reserva();

        reserva.setStatusReserva(StatusReserva.CANCELADA);

        assertThat(reserva.getStatusReserva()).isEqualTo(StatusReserva.CANCELADA);
    }

    @Test
    void shouldUpdateAllFields_whenAtualizarInformacoesCalled() {
        Usuario usuario = new Usuario(1L, "Joao", "joao@email.com");
        Sala sala = new Sala(1L, "Sala A", 10);
        LocalDateTime inicio = LocalDateTime.of(2025, 1, 20, 9, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 1, 20, 11, 0);
        Reserva reserva = new Reserva(usuario, sala, inicio, fim, 5);

        Usuario novoUsuario = new Usuario(2L, "Maria", "maria@email.com");
        Sala novaSala = new Sala(2L, "Sala B", 20);
        LocalDateTime novoInicio = LocalDateTime.of(2025, 2, 10, 14, 0);
        LocalDateTime novoFim = LocalDateTime.of(2025, 2, 10, 16, 0);
        ReservaUpdateRequest dto = new ReservaUpdateRequest(1L, 2L, 2L, novoInicio, novoFim, 10);

        reserva.atualizarInformacoes(novoUsuario, novaSala, dto);

        assertThat(reserva.getUsuario()).isEqualTo(novoUsuario);
        assertThat(reserva.getSala()).isEqualTo(novaSala);
        assertThat(reserva.getDataHoraInicio()).isEqualTo(novoInicio);
        assertThat(reserva.getDataHoraFim()).isEqualTo(novoFim);
        assertThat(reserva.getNumeroPessoas()).isEqualTo(10);
    }
}