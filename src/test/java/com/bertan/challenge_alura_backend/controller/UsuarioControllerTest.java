package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.dto.usuario.UsuarioRequest;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioResponse;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioUpdateRequest;
import com.bertan.challenge_alura_backend.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureJsonTesters
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private JacksonTester<UsuarioRequest> usuarioRequestJson;

    @Autowired
    private JacksonTester<UsuarioUpdateRequest> usuarioUpdateRequestJson;

    @Test
    void shouldReturnListOfUsers_whenUsersExist() throws Exception {
        UsuarioResponse response = new UsuarioResponse(1L, "Joao Silva", "joao@email.com");
        when(usuarioService.obterListaUsuarios()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Joao Silva"))
                .andExpect(jsonPath("$[0].email").value("joao@email.com"));

        verify(usuarioService).obterListaUsuarios();
    }

    @Test
    void shouldReturnUser_whenUserExists() throws Exception {
        UsuarioResponse response = new UsuarioResponse(1L, "Joao Silva", "joao@email.com");
        when(usuarioService.obterUsuarioPorId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Joao Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(usuarioService).obterUsuarioPorId(1L);
    }

    @Test
    void shouldReturn404_whenUserNotFound() throws Exception {
        when(usuarioService.obterUsuarioPorId(99L)).thenThrow(new EntityNotFoundException("Usuário não encontrado"));

        mockMvc.perform(get("/api/v1/usuarios/99"))
                .andExpect(status().isNotFound());

        verify(usuarioService).obterUsuarioPorId(99L);
    }

    @Test
    void shouldCreateUser_whenValidData() throws Exception {
        UsuarioRequest request = new UsuarioRequest("Novo Usuario", "novo@email.com");
        doNothing().when(usuarioService).criarUsuario(any(UsuarioRequest.class));

        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioRequestJson.write(request).getJson()))
                .andExpect(status().isCreated());

        verify(usuarioService).criarUsuario(any(UsuarioRequest.class));
    }

    @Test
    void shouldUpdateUser_whenUserExists() throws Exception {
        UsuarioUpdateRequest request = new UsuarioUpdateRequest(1L, "Usuario Atualizado", "atualizado@email.com");
        doNothing().when(usuarioService).atualizarUsuario(any(UsuarioUpdateRequest.class));

        mockMvc.perform(put("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioUpdateRequestJson.write(request).getJson()))
                .andExpect(status().isNoContent());

        verify(usuarioService).atualizarUsuario(any(UsuarioUpdateRequest.class));
    }

    @Test
    void shouldDeleteUser_whenUserExists() throws Exception {
        doNothing().when(usuarioService).deletarUsuario(1L);

        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());

        verify(usuarioService).deletarUsuario(1L);
    }
}