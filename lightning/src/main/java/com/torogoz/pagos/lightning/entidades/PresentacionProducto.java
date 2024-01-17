package com.torogoz.pagos.lightning.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "presentacion_producto")
public class PresentacionProducto {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presentacion_id", referencedColumnName = "id")
    private Presentacion presentacion;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;
    private BigDecimal precioVenta;
    private BigDecimal residuoMonto;
    private BigDecimal residuoPorcentaje;

    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getResiduoMonto() {
        return residuoMonto;
    }

    public void setResiduoMonto(BigDecimal residuoMonto) {
        this.residuoMonto = residuoMonto;
    }

    public BigDecimal getResiduoPorcentaje() {
        return residuoPorcentaje;
    }

    public void setResiduoPorcentaje(BigDecimal residuoPorcentaje) {
        this.residuoPorcentaje = residuoPorcentaje;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
