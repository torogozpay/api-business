package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="empresa")
@Setter
@Getter
public class Empresa {

    @Id
    @Column(unique=true, nullable=false)
    private Integer id;
    @Column(name = "nombre",length=255)
    private String nombre;
    @Column(name = "descripcion",length=255)
    private String descripcion;
    @Column(name = "activo",length=1)
    private Integer activo;

    @Column(name = "ln_address")
    private String lnAddress;

    public Empresa() {
    }

    public Empresa(Integer id) {
        this.id = id;
    }

}
