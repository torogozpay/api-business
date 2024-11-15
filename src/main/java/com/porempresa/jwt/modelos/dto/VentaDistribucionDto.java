package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VentaDistribucionDto {
    public String invoiceUid;
    public String ldAddress;
    public int amountSat;
    public int status;
    public String invoiceAddress;
    public int attempts;
    public String tipoAsociado;
}
