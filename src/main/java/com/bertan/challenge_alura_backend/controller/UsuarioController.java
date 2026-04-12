package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.dto.usuario.UsuarioRequest;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioResponse;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioUpdateRequest;
import com.bertan.challenge_alura_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obterListaUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.obterListaUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obterUsuarioPorId(@PathVariable Long id) {

        UsuarioResponse dto = usuarioService.obterUsuarioPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@Valid @RequestBody UsuarioRequest dto) {

        usuarioService.criarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> atualizarUsuario(@Valid @RequestBody UsuarioUpdateRequest dto) {

        usuarioService.atualizarUsuario(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {

        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
