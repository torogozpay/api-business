package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PresentacionDTO {

    private Integer id;
    private String nombre;
    private EmpresaDTO empresa;

}
