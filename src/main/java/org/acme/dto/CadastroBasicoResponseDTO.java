package org.acme.dto;

import org.acme.model.Cliente;

public record CadastroBasicoResponseDTO(
        Long id,
        String nome,
        String senha,
        String username,
        String cidade,
        String estado,
        String cpf,
        String cep
) {
    public static CadastroBasicoResponseDTO valueOf(Cliente cliente) {
        return new CadastroBasicoResponseDTO(
                cliente.getId(),
                cliente.getUsuario().getNome(),
                cliente.getUsuario().getSenha(),
                cliente.getUsuario().getUsername(),
                cliente.getCidade(),
                cliente.getEstado(),
                cliente.getCpf(),
                cliente.getCep()
        );
    }
}
