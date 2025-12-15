package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Pedido;
import org.acme.model.StatusPedido;
import org.acme.model.Usuario;

import java.time.LocalDateTime;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public PanacheQuery<Pedido> findByUsuario(String username) {
        if (username == null || username.isBlank()) {
            return find("1 = 0");
        }
        return find("UPPER(cliente.usuario.username) LIKE ?1 ORDER BY dataPedido DESC",
                "%" + username.toUpperCase() + "%");
    }

    public PanacheQuery<Pedido> findByCidade(String cidade) {
        return find("UPPER(enderecoentrega.cidade) LIKE ?1", "%" + cidade.toUpperCase() + "%");
    }
}