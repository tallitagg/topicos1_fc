package org.acme.dto;

import org.acme.model.Cliente;

public record ClienteResponseDTO(
    Long id,
    UsuarioResponseDTO usuario,
    String cidade,
    String estado,
    String cpf,
    String cep
) {
    public static ClienteResponseDTO valueOf(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                UsuarioResponseDTO.valueOf(cliente.getUsuario()),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.getCpf(),
                cliente.getCep()
        );
    }
}
