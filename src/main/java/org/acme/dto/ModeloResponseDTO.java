package org.acme.dto;

import org.acme.model.Modelo;

public record ModeloResponseDTO(
        Long id,
        String nome,
        Integer anoLancamento,
        Long marcaId
) {
    public static ModeloResponseDTO valueOf(Modelo modelo) {
        return new ModeloResponseDTO(
                modelo.getId(),
                modelo.getNome(),
                modelo.getAnoLancamento(),
                modelo.getMarca() != null ? modelo.getMarca().getId() : null
        );
    }
}
