package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Arquivo;

import java.util.Optional;

@ApplicationScoped
public class ArquivoRepository implements PanacheRepository<Arquivo> {

    public Optional<Arquivo> findByFid(String fid) {
        return find("fid", fid).firstResultOptional();
    }
}