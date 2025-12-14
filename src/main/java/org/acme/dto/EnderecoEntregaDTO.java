package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EnderecoEntregaDTO(
        @NotBlank String rua,
        @NotBlank String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        @NotBlank @Pattern(regexp = "\\d{8}") String cep
) {
}