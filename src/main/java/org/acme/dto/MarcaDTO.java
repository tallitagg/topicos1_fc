package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MarcaDTO(
        @NotBlank(message = "O nome da marca é obrigatório")
        String nome,

        @Size(max = 200, message = "O número máximo de modelos é 200")
        List<Long> modeloIds
) {
}
