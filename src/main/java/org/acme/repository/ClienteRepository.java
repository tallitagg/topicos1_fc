package org.acme.repository;

import org.acme.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {
    public List<Cliente> findByNome(String nome){

        if(nome == null){
            return null;
        }
        return find("UPPER(usuario.nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public Cliente findByUsernameAndSenha(String username, String senha) {
        if(username == null || senha == null){
            return null;
        }

        return find("usuario.username = ?1 AND usuario.senha = ?2", username, senha).firstResult();
    }

    public Cliente findByUsername(String username) {

        if (username == null){
            return null;
        }
        return find("usuario.username", username).firstResult();
    }

    public Cliente findByIdUsuario(Long idUsuario){
        return find("usuario.id", idUsuario).firstResult();
    }
}
