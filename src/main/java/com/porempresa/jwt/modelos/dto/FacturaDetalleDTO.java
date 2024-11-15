package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FacturaDetalleDTO {
    public int productId;
    public String productName;
    public int quantity;
    public BigDecimal subTotal;
    public Integer totalSats;
    public BigDecimal taxes;
    public BigDecimal grandTotal;
    public int packageTypeId;
}
