package org.acme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarPerfilDTO(
        @NotBlank(message = "A senha de confirmação é obrigatória.")
        String senhaConfirmacao,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres.")
        String nome,

        @NotBlank(message = "O username é obrigatório.")
        String username,

        @Email(message = "Informe um e-mail válido.")
        String email,

        String telefone,

        @NotBlank(message = "O CPF é obrigatório.")
        String cpf
) {
}