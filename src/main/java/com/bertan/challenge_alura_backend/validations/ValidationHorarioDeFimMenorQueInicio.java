package com.bertan.challenge_alura_backend.validations;

import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;
import com.bertan.challenge_alura_backend.exception.ValidationReservaException;
import org.springframework.stereotype.Component;

@Component
public class ValidationHorarioDeFimMenorQueInicio implements ValidationReservaRequest {

    @Override
    public void validate(ReservaRequest dto) {

        if (dto.dataHoraFim().isBefore(dto.dataHoraInicio()) || dto.dataHoraFim().isEqual(dto.dataHoraInicio())) {
            throw new ValidationReservaException("Horário de fim deve ser maior que o horário de início");
        }
    }
}
