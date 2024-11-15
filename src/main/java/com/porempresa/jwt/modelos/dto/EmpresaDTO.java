package com.porempresa.jwt.modelos.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer activo;
    private String apiId;
    private String secretId;
    private String lnAddress;

}
