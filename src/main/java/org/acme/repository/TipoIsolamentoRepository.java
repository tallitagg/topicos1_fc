package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.TipoIsolamento;

import java.util.List;

@ApplicationScoped
public class TipoIsolamentoRepository implements PanacheRepository<TipoIsolamento> {

    public List<TipoIsolamento> findByDescricao(String descricao) {
        return find("UPPER(descricao) LIKE ?1", "%" + descricao.toUpperCase() + "%").list();
    }

    public List<TipoIsolamento> findByEficienciaTermica(Long eficienciaTermica) {
        return find("eficienciaTermica = ?1", eficienciaTermica).list();
    }

}
