package org.acme.dto;

import jakarta.validation.constraints.NotEmpty;
import org.acme.model.Perfil;

import java.util.List;

public record CadastroBasicoDTO(
        String nome,
        @NotEmpty(message = "Já existe este username")
        String username,
        String email,
        String telefone,
        String senha,
        Perfil perfil,
        String cpf
) {
}