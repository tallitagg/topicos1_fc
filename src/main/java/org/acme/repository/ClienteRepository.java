package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Cliente;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public PanacheQuery<Cliente> findByNome(String nome) {
        if (nome == null || nome.isBlank()) return find("1=0");
        return find("UPPER(usuario.nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public PanacheQuery<Cliente> findByUsername(String username) {
        if (username == null || username.isBlank()) return find("1=0");
        return find("UPPER(usuario.username) LIKE ?1", "%" + username.toUpperCase() + "%");
    }

    public Cliente findByUsernameAndSenha(String username, String senha) {
        if (username == null || senha == null) return null;
        return find("usuario.username = ?1 AND usuario.senha = ?2", username, senha).firstResult();
    }

    public Cliente findByIdUsuario(Long idUsuario) {
        if (idUsuario == null) return null;
        return find("usuario.id = ?1", idUsuario).firstResult();
    }
}