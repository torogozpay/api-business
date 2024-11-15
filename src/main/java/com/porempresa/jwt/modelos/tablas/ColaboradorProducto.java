package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "colaborador_producto")
@Getter
@Setter
public class ColaboradorProducto {

    @EmbeddedId
    private ColaboradorProductoID id;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    private Boolean estado;

}
