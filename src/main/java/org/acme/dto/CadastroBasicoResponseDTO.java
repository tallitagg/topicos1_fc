package org.acme.dto;

import org.acme.model.Cliente;

public record CadastroBasicoResponseDTO(
        Long id,
        String nome,
        String username,
        String email,
        String telefone,
        String cpf
) {
    public static CadastroBasicoResponseDTO valueOf(Cliente cliente) {
        return new CadastroBasicoResponseDTO(
                cliente.getId(),
                cliente.getUsuario().getNome(),
                cliente.getUsuario().getUsername(),
                cliente.getUsuario().getEmail(),
                cliente.getUsuario().getTelefone(),
                cliente.getCpf()
        );
    }
}