package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.TipoTampa;

import java.util.List;

@ApplicationScoped
public class TipoTampaRepository implements PanacheRepository<TipoTampa> {

    public List<TipoTampa> findByDescricao(String descricao) {
        return find("UPPER(descricao) LIKE ?1", "%" + descricao.toUpperCase() + "%").list();
    }

    public List<TipoTampa> findByMaterial(String material) {
        return find("UPPER(material) LIKE ?1", "%" + material.toUpperCase() + "%").list();
    }

}
