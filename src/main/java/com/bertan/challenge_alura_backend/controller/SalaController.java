package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.dto.SalaRequest;
import com.bertan.challenge_alura_backend.dto.SalaResponse;
import com.bertan.challenge_alura_backend.dto.SalaUpdateRequest;
import com.bertan.challenge_alura_backend.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<List<SalaResponse>> listarSalas() {

        List<SalaResponse> salas = salaService.listarSalas();

        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponse> buscarSalaPorId(@PathVariable Long id) {

        SalaResponse sala = salaService.buscarSalaPorId(id);
        return ResponseEntity.ok(sala);
    }

    @PostMapping
    public ResponseEntity<String> criarSala(@RequestBody @Valid SalaRequest dto) {

        salaService.criarSala(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sala criada com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> atualizarSala(@RequestBody @Valid SalaUpdateRequest dto) {

        salaService.atualizarSala(dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarSala(@PathVariable Long id) {

        salaService.deletarSala(id);
        return ResponseEntity.noContent().build();
    }

}
