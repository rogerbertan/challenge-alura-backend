package com.bertan.challenge_alura_backend.domain;

import com.bertan.challenge_alura_backend.dto.sala.SalaRequest;
import com.bertan.challenge_alura_backend.dto.sala.SalaUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalaTest {

    @Test
    void shouldCreateSalaWithNullFields_whenNoArgsConstructorUsed() {
        Sala sala = new Sala();

        assertThat(sala.getId()).isNull();
        assertThat(sala.getNome()).isNull();
        assertThat(sala.getCapacidade()).isNull();
    }

    @Test
    void shouldUpdateNome_whenSetNomeCalled() {
        Sala sala = new Sala();

        sala.setNome("Sala Nova");

        assertThat(sala.getNome()).isEqualTo("Sala Nova");
    }

    @Test
    void shouldUpdateCapacidade_whenSetCapacidadeCalled() {
        Sala sala = new Sala();

        sala.setCapacidade(15);

        assertThat(sala.getCapacidade()).isEqualTo(15);
    }

    @Test
    void shouldSetNomeAndCapacidade_whenConstructedFromSalaRequest() {
        SalaRequest request = new SalaRequest("Sala de Reunioes", 20);

        Sala sala = new Sala(request);

        assertThat(sala.getNome()).isEqualTo("Sala de Reunioes");
        assertThat(sala.getCapacidade()).isEqualTo(20);
    }

    @Test
    void shouldUpdateNomeAndCapacidade_whenAtualizarInformacoesCalled() {
        Sala sala = new Sala(1L, "Sala Antiga", 10);
        SalaUpdateRequest dto = new SalaUpdateRequest(1L, "Sala Atualizada", 30);

        sala.atualizarInformacoes(dto);

        assertThat(sala.getNome()).isEqualTo("Sala Atualizada");
        assertThat(sala.getCapacidade()).isEqualTo(30);
    }
}