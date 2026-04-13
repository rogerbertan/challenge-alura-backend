package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Usuario;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioRequest;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioResponse;
import com.bertan.challenge_alura_backend.dto.usuario.UsuarioUpdateRequest;
import com.bertan.challenge_alura_backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> obterListaUsuarios() {

        return usuarioRepository.findAll()
                .stream().map(UsuarioResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obterUsuarioPorId(Long id) {

        return usuarioRepository.findById(id)
                .map(UsuarioResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + id));
    }

    @Transactional
    public void criarUsuario(UsuarioRequest dto) {

        Usuario usuario = new Usuario(dto);
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarUsuario(UsuarioUpdateRequest dto) {

        Usuario usuario = usuarioRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o id: " + dto.id()));
        usuario.atualizarInformacoes(dto);
    }

    @Transactional
    public void deletarUsuario(Long id) {

        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com o id: " + id);
        }

        usuarioRepository.deleteById(id);
    }
}
