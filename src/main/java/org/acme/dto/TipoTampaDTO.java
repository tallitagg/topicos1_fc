package org.acme.dto;

import jakarta.validation.constraints.NotBlank;

public record TipoTampaDTO(
        @NotBlank String descricao,
        String material
) {}
