package org.acme.dto;

public record AuthResponseDTO(
        String token,
        String tipo
) {
}