package org.acme.service;

import org.acme.dto.UsuarioResponseDTO;
import org.acme.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<Usuario> findAll();
    Usuario findByUsername(String username);
    Usuario findByUsernameAndSenha(String username, String senha);
    Usuario findById(Long id);
}