package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class DistribucionProductoID {

    @ManyToOne
    @JoinColumn(name="producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name="id_tipo_comision")
    private TipoDistribucion tipoDistribucion;

}
