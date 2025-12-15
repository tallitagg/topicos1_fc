package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Estado;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {
    public PanacheQuery<Estado> findByNome(Estado estado) {
        if (estado == null || estado.getId() == null)
            return null;

        return find("estado.id = ?1", estado.getId());
    }
}