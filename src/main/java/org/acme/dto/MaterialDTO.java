package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record MaterialDTO(
        @NotBlank
        String tipo,

        @PositiveOrZero
        Double resistenciaTemperatura
) {}
