package org.acme.dto;

import org.acme.model.EnderecoEntrega;

public record EnderecoEntregaResponseDTO(
        Long id,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        String cep
) {
    public static EnderecoEntregaResponseDTO valueOf(EnderecoEntrega enderecoEntrega) {
        return new EnderecoEntregaResponseDTO(
                enderecoEntrega.getId(),
                enderecoEntrega.getRua(),
                enderecoEntrega.getNumero(),
                enderecoEntrega.getComplemento(),
                enderecoEntrega.getBairro(),
                enderecoEntrega.getCidade(),
                enderecoEntrega.getEstado(),
                enderecoEntrega.getCep()
        );
    }
}