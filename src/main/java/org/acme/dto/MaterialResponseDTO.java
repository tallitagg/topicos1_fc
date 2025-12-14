package org.acme.dto;

import org.acme.model.Material;

public record MaterialResponseDTO(
        Long id,
        String tipo,
        Double resistenciaTemperatura
) {
    public static MaterialResponseDTO valueOf(Material material) {
        return new MaterialResponseDTO(
                material.getId(),
                material.getTipo(),
                material.getResistenciaTemperatura()
        );
    }
}
