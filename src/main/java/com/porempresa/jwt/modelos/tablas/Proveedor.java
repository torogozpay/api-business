package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="proveedor")
@Setter
@Getter
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private Integer id;

    @Column(name = "nombres",length=255)
    private String nombres;

    @Column(name = "apellidos",length=255)
    private String apellidos;

    @Column(name = "bitcoin_address",length=255)
    private String bitcoin_address;

    @Column(name = "lightning_address",length=255)
    private String lightning_address;

    @Column(name = "wallet_alternativo",length=255)
    private String wallet_alternativo;

    @ManyToOne
    @JoinColumn(name="empresa_id")
    private Empresa empresa_id;




}
