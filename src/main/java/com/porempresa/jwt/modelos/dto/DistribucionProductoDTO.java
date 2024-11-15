package com.porempresa.jwt.modelos.dto;

import com.porempresa.jwt.modelos.tablas.Producto;
import com.porempresa.jwt.modelos.tablas.TipoDistribucion;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DistribucionProductoDTO {

    private Integer estado;
    private BigDecimal porcentaje;
    private ProductoDTO producto;
    private TipoDistribucionDTO tipoDistribucion;

}
