package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ModeloDTO(
        @NotBlank
        String nome,

        @Positive
        Integer anoLancamento,

        Long marcaId
) {}

