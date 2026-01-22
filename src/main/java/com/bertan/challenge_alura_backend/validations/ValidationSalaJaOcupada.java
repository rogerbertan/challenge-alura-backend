package com.bertan.challenge_alura_backend.validations;

import com.bertan.challenge_alura_backend.domain.StatusReserva;
import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.exception.ValidationReservaException;
import com.bertan.challenge_alura_backend.repository.ReservaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidationSalaJaOcupada implements ValidationReservaRequest {

    private final ReservaRepository reservaRepository;

    public ValidationSalaJaOcupada(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void validar(ReservaRequest dto) {

        boolean salaOcupada = reservaRepository.existsBySalaIdAndStatusAndDataHoraInicioLessThanAndDataHoraFimGreaterThan(
                dto.salaId(),
                StatusReserva.ATIVA,
                dto.dataHoraFim(),
                dto.dataHoraInicio()
        );

        if (salaOcupada) {
            throw new ValidationReservaException("Sala já está ocupada nesse período");
        }
    }
}
