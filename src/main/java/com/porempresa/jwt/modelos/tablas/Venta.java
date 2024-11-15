package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name="venta")
@Setter
@Getter
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(unique=true, nullable=false)
    private String uid;

    @Column(name = "estado",length=1)
    private String estado;

    @Column(name = "fecha_tx",length=255)
    private Date fechaTx;

    @Column(name = "invoice_address",length=255)
    private String invoiceAddress;

    @ManyToOne
    @JoinColumn(name="empresa_id")
    private Empresa empresaId;

    @Column(name="presentacion_id")
    private Integer presentacion;

    @Column(name="producto_id")
    private Integer producto;

    @Column(name="proveedor_id")
    private Integer proveedor;


    @Column(name="order_id")
    public int orderId;
    @Column(name="customer_name")
    public String customerName;
    @Column(name="customer_email")
    public String customerEmail;
    public String currency;
    public BigDecimal subtotal;
    public BigDecimal taxes;
    @Column(name="total_amount")
    public BigDecimal totalAmount;
    @Column(name="invoice_date")
    public Date invoiceDate;
    @Column(name="invoice_stamp")
    private String invoiceStamp;

    @Column(name="apply_split")
    private boolean applySplit;
    private BigDecimal shipping;
    @Column(name="amount_sat")
    public Integer amountSat;

    @Column(name = "fecha_creacion", columnDefinition = "")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;


    private boolean distributed;
    public Integer totalSats;
}
