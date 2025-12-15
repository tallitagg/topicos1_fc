package org.acme.dto;

import org.acme.model.Estado;
import org.acme.model.Regiao;

public record EstadoResponseDTO(
        Long id,
        String nome,
        String sigla,
        Regiao regiao
) {
    public static EstadoResponseDTO valueOf(Estado estado) {
        return new EstadoResponseDTO(
                estado.getId(),
                estado.getNome(),
                estado.getSigla(),
                estado.getRegiao()
        );
    }
}