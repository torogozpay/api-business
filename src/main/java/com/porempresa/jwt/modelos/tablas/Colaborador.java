package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "colaborador")
@Setter
@Getter
public class Colaborador {
    @Id
    private int id;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    //@Column(name = "propina_monto_m_sats")
    //private int propinaMontoMsats; no debe ir configurado por colaborador para evitar asignarle solo a un colab el 100% de la distribucion
    private String lnAddress;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}
