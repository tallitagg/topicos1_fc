package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Produto;

import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByNome(String nome) {
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Produto> findByPreco(Long preco) {
        return find("preco = ?1", preco).list();
    }

    public List<Produto> findByMarca(String marca) {
        return find("""
                select p from Produto p
                join p.marca m
                where upper(m.nome) like ?1
                """, "%" + marca.toUpperCase() + "%").list();
    }

    public List<Produto> findByModelo(String modelo) {
        return find("""
                select p from Produto p
                join p.modelo mo
                where upper(mo.nome) like ?1
                """, "%" + modelo.toUpperCase() + "%").list();
    }

    public List<Produto> findByMaterial(String material) {
        return find("""
                select p from Produto p
                join p.material mat
                where upper(mat.tipo) like ?1
                """, "%" + material.toUpperCase() + "%").list();
    }

    public List<Produto> findByCapacidade(Double capacidade) {
        return find("capacidade = ?1", capacidade).list();
    }
}
