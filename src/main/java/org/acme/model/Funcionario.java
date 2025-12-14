package org.acme.model;

import jakarta.persistence.*;

@Entity
@Table(name = "funcionario")
public class Funcionario extends DefaultEntity{
    @Column(nullable = false)
    private Double salario;

    private String cargo;


    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
