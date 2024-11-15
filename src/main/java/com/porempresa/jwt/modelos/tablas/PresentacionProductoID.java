package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class PresentacionProductoID {

    @ManyToOne
    @JoinColumn(name="presentacion_id")
    private Presentacion presentacion;

    @ManyToOne
    @JoinColumn(name="proveedor_id")
    private Proveedor proveedor;

}
