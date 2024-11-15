package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TipoDistribucionDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer puntaje;
    private EmpresaDTO empresa;

}
