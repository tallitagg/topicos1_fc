package org.acme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "modelo")
public class Modelo extends DefaultEntity{

    @Column(name = "nome",nullable = false)
    private String nome;

    @Column(name = "anoLancamento")
    private Integer anoLancamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
