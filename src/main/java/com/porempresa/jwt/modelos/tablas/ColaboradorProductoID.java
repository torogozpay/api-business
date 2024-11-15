package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class ColaboradorProductoID {
    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
