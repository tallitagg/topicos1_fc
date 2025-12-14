package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MarcaDTO(
        @NotBlank
        String nome,

        @Size(max = 200)
        List<Long> modeloIds
) {
}
