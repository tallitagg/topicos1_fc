package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Modelo;

import java.util.List;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {

    public List<Modelo> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Modelo> findByMarcaNome(String nomeMarca) {
        return find("UPPER(marca.nome) LIKE ?1", "%" + nomeMarca.toUpperCase() + "%").list();
    }

    public List<Modelo> findByAnoLancamento(Integer anoLancamento) {
        return find("anoLancamento = ?1", anoLancamento).list();
    }

}
