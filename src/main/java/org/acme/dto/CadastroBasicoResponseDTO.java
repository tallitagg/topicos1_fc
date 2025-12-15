package org.acme.dto;

import org.acme.model.Cliente;

import java.util.List;

public record CadastroBasicoResponseDTO(
        Long id,
        String nome,
        String senha,
        String username,
        String cpf
) {
    public static CadastroBasicoResponseDTO valueOf(Cliente cliente) {

        return new CadastroBasicoResponseDTO(
                cliente.getId(),
                cliente.getUsuario().getNome(),
                cliente.getUsuario().getSenha(),
                cliente.getUsuario().getUsername(),
                cliente.getCpf()
        );
    }
}