package org.acme.dto;

import jakarta.validation.constraints.NotEmpty;

public record CadastroBasicoDTO(
        String nome,
        @NotEmpty(message = "Já existe este username")
        String username,
        String senha,
        String perfil,
        String cidade,
        String estado,
        String cpf,
        String cep
) {
}
