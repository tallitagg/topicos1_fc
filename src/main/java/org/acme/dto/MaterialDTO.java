package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record MaterialDTO(
        @NotBlank (message = "O tipo do material é obrigatório")
        String tipo,

        @PositiveOrZero (message = "A resistência à temperatura deve ser um valor positivo ou zero")
        Double resistenciaTemperatura
) {}
