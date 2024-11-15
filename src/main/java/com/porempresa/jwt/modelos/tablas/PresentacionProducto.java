package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="presentacion_producto")
@Setter
@Getter
public class PresentacionProducto {

    @EmbeddedId
    private PresentacionProductoID id;
    @Column(name = "precio_venta")
    private BigDecimal precio_venta;
    @Column(name = "residuo_monto")
    private BigDecimal residuo_monto;
    @Column(name = "residuo_porcentaje")
    private BigDecimal residuo_porcentaje;
    @ManyToOne
    @JoinColumn(name="producto_id")
    private Producto producto;

}
