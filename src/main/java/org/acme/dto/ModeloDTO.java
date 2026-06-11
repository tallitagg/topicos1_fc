package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ModeloDTO(
        @NotBlank(message = "O nome do modelo é obrigatório")
        String nome,

        @Positive(message = "O ano de lançamento deve ser um valor positivo")
        Integer anoLancamento,

        Long marcaId
) {}

