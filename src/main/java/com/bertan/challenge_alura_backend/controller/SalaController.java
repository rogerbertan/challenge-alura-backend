package com.bertan.challenge_alura_backend.controller;

import com.bertan.challenge_alura_backend.dto.SalaResponse;
import com.bertan.challenge_alura_backend.service.SalaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
