package com.bertan.challenge_alura_backend.validations;

import com.bertan.challenge_alura_backend.dto.reserva.ReservaRequest;

public interface ValidationReservaRequest {

    void validate(ReservaRequest dto);
}
