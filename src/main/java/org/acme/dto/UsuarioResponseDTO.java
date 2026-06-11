package org.acme.dto;

import org.acme.model.Perfil;
import org.acme.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String email,
        String telefone,
        String nome,
        String senha,
        Perfil perfil
) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getNome(),
                usuario.getSenha(),
                usuario.getPerfil());
    }
}
