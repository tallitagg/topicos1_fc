package org.acme.dto;

public record ClienteDTO(
        String nome,
        String username,
        String cidade,
        String estado,
        String senha,
        String cpf,
        String cep
) {}
