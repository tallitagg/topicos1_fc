package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Pedido;
import org.acme.model.Usuario;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {
    public PanacheQuery<Pedido> findByUsuario(Usuario usuario) {
        if(usuario == null || usuario.getId() == null)
            return null;
        return find("usuario.id = ?1 ", usuario.getId());
    }
}
