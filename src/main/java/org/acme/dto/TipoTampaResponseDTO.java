package org.acme.dto;

import org.acme.model.TipoTampa;

public record TipoTampaResponseDTO(
        Long id,
        String descricao,
        String material
) {
    public static TipoTampaResponseDTO valueOf(TipoTampa tipoTampa) {
        return new TipoTampaResponseDTO(
                tipoTampa.getId(),
                tipoTampa.getDescricao(),
                tipoTampa.getMaterial()
        );
    }
}
