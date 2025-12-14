package org.acme.dto;

import jakarta.validation.constraints.NotBlank;

public record TipoIsolamentoDTO(
        @NotBlank
        String descricao,

        @NotBlank
        Double eficienciaTermica
) {}
