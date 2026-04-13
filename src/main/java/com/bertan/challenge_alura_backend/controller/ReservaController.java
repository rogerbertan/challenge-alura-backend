package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaResponse;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaUpdateRequest;
import com.bertan.challenge_alura_backend.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<Page<ReservaResponse>> listarReservas(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {

        Page<ReservaResponse> reservas = reservaService.listarReservas(page, size);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReservaResponse> obterReservaPorId(@PathVariable Long id) {

        ReservaResponse reserva = reservaService.obterReservaPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<String> criarReserva(@Valid @RequestBody ReservaRequest dto) {

        reservaService.criarReserva(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Reserva criada com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> atualizarReserva(@Valid @RequestBody ReservaUpdateRequest dto) {

        reservaService.atualizarReserva(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Long id) {

        reservaService.cancelarReserva(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reserva cancelada com sucesso");
    }
}
