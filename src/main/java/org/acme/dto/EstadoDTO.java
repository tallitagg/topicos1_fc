package org.acme.dto;

import org.acme.model.Regiao;

public record EstadoDTO(
        String nome,
        String sigla,
        Regiao regiao
) {
}