package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="tipo_distribucion")
@Setter
@Getter
public class TipoDistribucion implements Serializable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Integer id;

    @Column(name = "nombre",length=255)
    private String nombre;

    @Column(name = "descripcion",length=255)
    private String descripcion;

    @Column(name = "puntaje",length=255)
    private Integer puntaje;

    @ManyToOne
    @JoinColumn(name="empresa_id")
    private Empresa empresa_id;

}
