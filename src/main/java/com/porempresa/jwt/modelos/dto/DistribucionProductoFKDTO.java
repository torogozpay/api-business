package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DistribucionProductoFKDTO {

    private ProductoDTO producto;
    private TipoDistribucionDTO tipoDistribucion;

}
