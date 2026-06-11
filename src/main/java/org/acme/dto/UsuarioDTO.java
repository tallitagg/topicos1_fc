package org.acme.dto;

public record UsuarioDTO(
        String nome,
        String username,
        String senha,
        String email,
        String telefone,
        int idPerfil
) {
}
