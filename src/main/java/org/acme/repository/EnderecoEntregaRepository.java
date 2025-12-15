package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.EnderecoEntrega;

@ApplicationScoped
public class EnderecoEntregaRepository implements PanacheRepository<EnderecoEntrega> {
}