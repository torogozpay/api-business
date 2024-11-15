package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DistribucionPropinaDTO {

    private String id;
    private VentaDTO venta;
    private ProveedorDTO proveedor;
    private DistribucionProductoFKDTO distribucionProducto;


}
