package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name="distribucion_producto")
@Setter
@Getter
public class DistribucionProducto {

    @EmbeddedId
    private DistribucionProductoID id;
    @Column(name = "estado",length=255)
    private Integer estado;
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;
    @Column(name="monto")
    private BigDecimal monto;


}
