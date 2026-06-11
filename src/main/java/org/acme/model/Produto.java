package org.acme.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
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
    private Integer estoque;

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

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "produto_arquivo",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "arquivo_id", unique = true)
    )
    private List<Arquivo> arquivos = new ArrayList<>();

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


    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
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


    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
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


    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
    }

    public void addArquivo(Arquivo arquivo) {
        if (arquivo == null) {
            return;
        }

        arquivos.add(arquivo);
    }

    public void removeArquivo(Arquivo arquivo) {
        if (arquivo == null) {
            return;
        }

        arquivos.remove(arquivo);
    }
}