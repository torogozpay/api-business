package com.torogoz.pagos.lightning.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "distribucion_propina")
public class DistribucionPropina {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "venta_id", referencedColumnName = "uId", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;

    @JoinColumns({
        @JoinColumn(name = "distpr_prod_id", referencedColumnName = "producto_id"),
            @JoinColumn(name = "distpr_dist_id", referencedColumnName = "tipo_dist_id")
    })
    @ManyToOne(fetch = FetchType.EAGER)
    private DistribucionProducto distrProducto;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public DistribucionProducto getDistrProducto() {
        return distrProducto;
    }

    public void setDistrProducto(DistribucionProducto distrProducto) {
        this.distrProducto = distrProducto;
    }
}
