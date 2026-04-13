package com.bertan.challenge_alura_backend.service;

import com.bertan.challenge_alura_backend.domain.Reserva;
import com.bertan.challenge_alura_backend.domain.Sala;
import com.bertan.challenge_alura_backend.domain.Usuario;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaResponse;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaUpdateRequest;
import com.bertan.challenge_alura_backend.repository.ReservaRepository;
import com.bertan.challenge_alura_backend.repository.SalaRepository;
import com.bertan.challenge_alura_backend.repository.UsuarioRepository;
import com.bertan.challenge_alura_backend.validations.ValidationReservaRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;
    private final List<ValidationReservaRequest> validations;

    public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository, List<ValidationReservaRequest> validations) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.validations = validations;
    }

    @Transactional(readOnly = true)
    public Page<ReservaResponse> listarReservas(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return reservaRepository.findAll(pageable)
                .map(ReservaResponse::new);
    }

    @Transactional(readOnly = true)
    public ReservaResponse obterReservaPorId(Long id) {

        return reservaRepository.findById(id)
                .map(ReservaResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
    }

    @Transactional
    public void criarReserva(ReservaRequest dto) {

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        validations.forEach(v -> v.validar(dto));

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

        Reserva reserva = reservaRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));

        ReservaRequest reservaRequest = new ReservaRequest(
                dto.usuarioId(),
                dto.salaId(),
                dto.dataHoraInicio(),
                dto.dataHoraFim(),
                dto.numeroPessoas()
        );

        validations.forEach(v -> v.validar(reservaRequest));

        Sala sala = salaRepository.findById(dto.salaId())
                .orElseThrow(() -> new EntityNotFoundException("Sala não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        reserva.atualizarInformacoes(usuario, sala, dto);
    }

    @Transactional
    public void cancelarReserva(Long id) {

        Reserva reserva = reservaRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Reserva não encontrada"));
        reserva.cancelarReserva();
    }
}
