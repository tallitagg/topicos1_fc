package org.acme.dto;

import org.acme.model.Usuario;

public record AuthResponseDTO(
        Long id,
        String username,
        String senha
) {
    public static AuthResponseDTO valueOf(Usuario usuario) {
        return new AuthResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getSenha()
        );
    }
}
