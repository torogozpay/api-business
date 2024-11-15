package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductoDTO {

    private Integer id;
    private String descripcion;
    private String nombre;
    private Integer puntaje;
    private EmpresaDTO empresa;

}
