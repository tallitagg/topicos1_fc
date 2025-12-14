package org.acme.dto;

public record UsuarioDTO(
        String nome,
        String username,
        String senha,
        int idPerfil
) {
}
