package com.bertan.challenge_alura_backend.domain;

import com.bertan.challenge_alura_backend.dto.usuario.UsuarioRequest;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioUpdateRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {

    @Test
    void shouldCreateUsuarioWithNullFields_whenNoArgsConstructorUsed() {
        Usuario usuario = new Usuario();

        assertThat(usuario.getId()).isNull();
        assertThat(usuario.getNome()).isNull();
        assertThat(usuario.getEmail()).isNull();
    }

    @Test
    void shouldUpdateNome_whenSetNomeCalled() {
        Usuario usuario = new Usuario();

        usuario.setNome("Carlos");

        assertThat(usuario.getNome()).isEqualTo("Carlos");
    }

    @Test
    void shouldUpdateEmail_whenSetEmailCalled() {
        Usuario usuario = new Usuario();

        usuario.setEmail("carlos@email.com");

        assertThat(usuario.getEmail()).isEqualTo("carlos@email.com");
    }

    @Test
    void shouldSetNomeAndEmail_whenConstructedFromUsuarioRequest() {
        UsuarioRequest request = new UsuarioRequest("Ana", "ana@email.com");

        Usuario usuario = new Usuario(request);

        assertThat(usuario.getNome()).isEqualTo("Ana");
        assertThat(usuario.getEmail()).isEqualTo("ana@email.com");
    }

    @Test
    void shouldUpdateNomeAndEmail_whenAtualizarInformacoesCalled() {
        Usuario usuario = new Usuario(1L, "Joao", "joao@email.com");
        UsuarioUpdateRequest dto = new UsuarioUpdateRequest(1L, "Joao Atualizado", "joao.novo@email.com");

        usuario.atualizarInformacoes(dto);

        assertThat(usuario.getNome()).isEqualTo("Joao Atualizado");
        assertThat(usuario.getEmail()).isEqualTo("joao.novo@email.com");
    }
}