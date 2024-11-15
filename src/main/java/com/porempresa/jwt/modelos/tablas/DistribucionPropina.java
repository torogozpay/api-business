package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="distribucion_propina")
@Setter
@Getter
public class DistribucionPropina {

    @Id
    @Column(name = "u_id",unique=true, nullable=false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String u_id;
    @ManyToOne
    @JoinColumn(name="venta_id")
    private Venta ventaId;
    @ManyToOne
    @JoinColumn(name="proveedor_id")
    private Proveedor proveedor_id;
    @ManyToOne
    @JoinColumn(name="distpr_prod_id")
    private Producto producto;
    @ManyToOne
    @JoinColumn(name="distpr_dist_id")
    private TipoDistribucion tipoDistribucion;



    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "colaborador_id"),
            @JoinColumn(name = "producto_id")
    })
    private ColaboradorProducto colabProd;

    @Column(name = "amount_sat")
    private BigDecimal amountSat;
    private Boolean status;
    @Column(name = "invoice_address")
    private String invoiceAddress;
    private Integer attempts;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Column(name = "tipo_asociado")
    private String tipoAsociado;//opciones: colaborador, comercio, torogoz
}
