package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Marca;

import java.util.List;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

    public List<Marca> findByNome(String nome) {
        return find("""
        select distinct m
        from Marca m
        left join fetch m.modelos
        where upper(m.nome) like ?1
    """, "%" + nome.toUpperCase() + "%").list();
    }

}
