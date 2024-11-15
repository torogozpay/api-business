package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "comision_comercio")
@Setter
@Getter
public class ComisionComercio {
    @Id
    public int comisionId;
    public int tipoComisionId;

    public String nombreComision;
    public BigDecimal valorComision;
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
}
