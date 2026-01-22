package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.domain.Usuario;
import com.bertan.challenge_alura_backend.dto.ReservaRequest;
import com.bertan.challenge_alura_backend.dto.ReservaResponse;
import com.bertan.challenge_alura_backend.dto.ReservaUpdateRequest;
import com.bertan.challenge_alura_backend.repository.ReservaRepository;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import com.bertan.challenge_alura_backend.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;

    public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
    }

    public Page<ReservaResponse> listarReservas(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return reservaRepository.findAll(pageable)
                .map(ReservaResponse::new);
    }

    public ReservaResponse obterReservaPorId(Long id) {

        return reservaRepository.findById(id)
                .map(ReservaResponse::new)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional
    public void criarReserva(ReservaRequest dto) {

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        //validations

        Reserva reserva = new Reserva(
                usuario,
                sala,
                dto.dataHoraInicio(),
                dto.dataHoraFim(),
                dto.numeroPessoas()
        );

        reservaRepository.save(reserva);
    }

    @Transactional
    public void atualizarReserva(ReservaUpdateRequest dto) {

        if (!reservaRepository.existsById(dto.id())) {
            throw new RuntimeException("Reserva não encontrada");
        }

        Reserva reserva = reservaRepository.getReferenceById(dto.id());

        //validations

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new RuntimeException("Sala não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        reserva.atualizarInformacoes(usuario, sala, dto);
    }

    @Transactional
    public void cancelarReserva(Long id) {

        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva não encontrada");
        }

        Reserva reserva = reservaRepository.getReferenceById(id);
        reserva.cancelarReserva();
    }
}
