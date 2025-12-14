package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Material;

import java.util.List;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material> {

    public List<Material> findByTipo(String tipo) {
        return find("UPPER(tipo) LIKE ?1", "%" + tipo.toUpperCase() + "%").list();
    }

}
