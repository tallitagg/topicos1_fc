package org.acme.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cor")
public class Cor extends DefaultEntity{

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, length = 7)
    private String codigoHex;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoHex() {
        return codigoHex;
    }

    public void setCodigoHex(String codigoHex) {
        this.codigoHex = codigoHex;
    }
}
