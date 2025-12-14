package org.acme.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "produto")
public class Produto extends DefaultEntity {

    @Column(length = 250)
    private String nome;

    @Column(length = 1000)
    private String descricao;

    @Column
    private Double preco;

    @Column(nullable = false)
    private Double capacidade;

    @Column(nullable = false)
    private Integer Estoque;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipoTampa", nullable = false)
    private TipoTampa tipoTampa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipoIsolamento", nullable = false)
    private TipoIsolamento tipoIsolamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_material", nullable = false)
    private Material material;

    @ManyToMany
    @JoinTable(
            name = "produto_cor",
            joinColumns = @JoinColumn(name = "id_produto"),
            inverseJoinColumns = @JoinColumn(name = "id_cor")
    )
    private Set<Cor> cores;

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Integer getEstoque() {
        return Estoque;
    }

    public void setEstoque(Integer estoque) {
        Estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Double getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Double capacidade) {
        this.capacidade = capacidade;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public TipoTampa getTipoTampa() {
        return tipoTampa;
    }

    public void setTipoTampa(TipoTampa tipoTampa) {
        this.tipoTampa = tipoTampa;
    }

    public TipoIsolamento getTipoIsolamento() {
        return tipoIsolamento;
    }

    public void setTipoIsolamento(TipoIsolamento tipoIsolamento) {
        this.tipoIsolamento = tipoIsolamento;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Set<Cor> getCores() {
        return cores;
    }

    public void setCores(Set<Cor> cores) {
        this.cores = cores;
    }
}
