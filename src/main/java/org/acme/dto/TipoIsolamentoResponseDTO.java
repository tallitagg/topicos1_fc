package org.acme.dto;

import org.acme.model.TipoIsolamento;

public record TipoIsolamentoResponseDTO(
        Long id,
        String descricao,
        Double eficienciaTermica
) {
    public static TipoIsolamentoResponseDTO valueOf(TipoIsolamento tipoIsolamento) {
        return new TipoIsolamentoResponseDTO(
                tipoIsolamento.getId(),
                tipoIsolamento.getDescricao(),
                tipoIsolamento.getEficienciaTermica()
        );
    }
}
