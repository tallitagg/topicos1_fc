package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.UsuarioResponseDTO;
import org.acme.model.Usuario;
import org.acme.repository.UsuarioRepository;

import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    UsuarioRepository repository;
    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> findAll() {
        return repository.listAll();
    }

    @Override
    public Usuario findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return repository.findByUsernameSenha(username, senha);
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null)
            return null;

        return usuario;
    }
}