package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CorDTO(

        @NotBlank
        String nome,

        @NotBlank
        @Size(min = 7, max = 7)
        @Pattern(regexp = "^#[0-9A-Fa-f]{6}$")
        String codigoHex
) {}
