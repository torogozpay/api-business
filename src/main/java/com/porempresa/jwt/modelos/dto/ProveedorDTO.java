package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProveedorDTO {

    private Integer id;
    private String apellidos;
    private String bitcoin;
    private String lightning;
    private String nombres;
    private String wallet;
    private EmpresaDTO empresa;

}
