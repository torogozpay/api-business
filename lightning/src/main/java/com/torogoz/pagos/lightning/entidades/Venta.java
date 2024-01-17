package com.torogoz.pagos.lightning.entidades;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uId;
    private String invoiceAddress;
    private Date fechaTx;
    private char estado;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;
    @JoinColumns({
            @JoinColumn(name = "pp_presentacion_id", referencedColumnName = "presentacion_id"),
            @JoinColumn(name = "pp_proveedor_id", referencedColumnName = "proveedor_id"),
            @JoinColumn(name = "pp_producto_id", referencedColumnName = "producto_id")
    })
    @ManyToOne
    private PresentacionProducto presentacionProducto;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Date getFechaTx() {
        return fechaTx;
    }

    public void setFechaTx(Date fechaTx) {
        this.fechaTx = fechaTx;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public PresentacionProducto getPresentacionProducto() {
        return presentacionProducto;
    }

    public void setPresentacionProducto(PresentacionProducto presentacionProducto) {
        this.presentacionProducto = presentacionProducto;
    }

}
