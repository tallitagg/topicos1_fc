package org.acme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_isolamento")
public class TipoIsolamento extends DefaultEntity {

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Double eficienciaTermica;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getEficienciaTermica() {
        return eficienciaTermica;
    }

    public void setEficienciaTermica(Double eficienciaTermica) {
        this.eficienciaTermica = eficienciaTermica;
    }
}
