package com.porempresa.jwt.modelos.dto;

import com.porempresa.jwt.modelos.tablas.Presentacion;
import com.porempresa.jwt.modelos.tablas.PresentacionProductoID;
import com.porempresa.jwt.modelos.tablas.Producto;
import com.porempresa.jwt.modelos.tablas.Proveedor;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PresentacionProductoDTO {

    private PresentacionDTO presentacion;
    private ProveedorDTO proveedor;
    private BigDecimal precioVenta;
    private BigDecimal residuoMonto;
    private BigDecimal residuoPorcentaje;
    private ProductoDTO producto;

}
