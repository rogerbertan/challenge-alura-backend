package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Usuario;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioRequest;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioResponse;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioUpdateRequest;
import com.bertan.challenge_alura_backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Joao Silva", "joao@email.com");
    }

    @Test
    void shouldReturnListOfUsers_whenUsersExist() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        List<UsuarioResponse> result = usuarioService.obterListaUsuarios();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).id()).isEqualTo(1L);
        assertThat(result.get(0).nome()).isEqualTo("Joao Silva");
        assertThat(result.get(0).email()).isEqualTo("joao@email.com");
        verify(usuarioRepository).findAll();
    }

    @Test
    void shouldReturnEmptyList_whenNoUsersExist() {
        when(usuarioRepository.findAll()).thenReturn(Collections.emptyList());

        List<UsuarioResponse> result = usuarioService.obterListaUsuarios();

        assertThat(result).isEmpty();
        verify(usuarioRepository).findAll();
    }

    @Test
    void shouldReturnUser_whenUserExists() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponse result = usuarioService.obterUsuarioPorId(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.nome()).isEqualTo("Joao Silva");
        assertThat(result.email()).isEqualTo("joao@email.com");
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUserNotFound() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.obterUsuarioPorId(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado com o id: 99");
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void shouldSaveUser_whenValidData() {
        UsuarioRequest request = new UsuarioRequest("Novo Usuario", "novo@email.com");

        usuarioService.criarUsuario(request);

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void shouldUpdateUser_whenUserExists() {
        UsuarioUpdateRequest request = new UsuarioUpdateRequest(1L, "Usuario Atualizado", "atualizado@email.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.atualizarUsuario(request);

        verify(usuarioRepository).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenUpdatingNonExistentUser() {
        UsuarioUpdateRequest request = new UsuarioUpdateRequest(99L, "Usuario Inexistente", "inexistente@email.com");
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.atualizarUsuario(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado com o id: 99");
        verify(usuarioRepository).findById(99L);
    }

    @Test
    void shouldDeleteUser_whenUserExists() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).delete(usuario);
    }

    @Test
    void shouldThrowEntityNotFoundException_whenDeletingNonExistentUser() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.deletarUsuario(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Usuário não encontrado com o id: 99");
        verify(usuarioRepository).findById(99L);
        verify(usuarioRepository, never()).delete(any());
    }
}