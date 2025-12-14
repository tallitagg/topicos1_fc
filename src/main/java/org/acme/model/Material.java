package org.acme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "material")
public class Material extends DefaultEntity{

    @Column(nullable = false)
    private String tipo;

    @Column(name = "resistencia_temperatura")
    private Double resistenciaTemperatura;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getResistenciaTemperatura() {
        return resistenciaTemperatura;
    }

    public void setResistenciaTemperatura(Double resistenciaTemperatura) {
        this.resistenciaTemperatura = resistenciaTemperatura;
    }
}
