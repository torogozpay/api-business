package com.torogoz.pagos.lightning.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "distribucion_producto")
public class DistribucionProducto {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_dist_id", referencedColumnName = "id")
    private TIpoDistribucion tipoDistribucion;

    private boolean estado;

    private BigDecimal porcentaje;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public TIpoDistribucion getTipoDistribucion() {
        return tipoDistribucion;
    }

    public void setTipoDistribucion(TIpoDistribucion tipoDistribucion) {
        this.tipoDistribucion = tipoDistribucion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
}
