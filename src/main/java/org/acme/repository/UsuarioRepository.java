package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Usuario;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public Usuario findByUsernameSenha(String username, String senha) {
        return find("SELECT u FROM Usuario u WHERE u.username = ?1 AND u.senha = ?2 ", username, senha).firstResult();
    }

    public Usuario findByUsername(String username) {
        return find("SELECT u FROM Usuario u WHERE u.username = ?1 ", username).firstResult();
    }

}