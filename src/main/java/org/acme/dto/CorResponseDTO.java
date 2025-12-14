package org.acme.dto;

import org.acme.model.Cor;

public record CorResponseDTO(
        Long id,
        String nome,
        String codigoHex
) {
    public static CorResponseDTO valueOf(Cor cor) {
        return new CorResponseDTO(
                cor.getId(),
                cor.getNome(),
                cor.getCodigoHex()
        );
    }
}
