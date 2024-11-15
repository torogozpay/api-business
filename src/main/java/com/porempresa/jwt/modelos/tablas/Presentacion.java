package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="presentacion")
@Setter
@Getter
public class Presentacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Integer id;
    @Column(name = "nombre",length=255)
    private String nombre;
    @ManyToOne
    @JoinColumn(name="empresa_id")
    private Empresa empresa_id;

}
