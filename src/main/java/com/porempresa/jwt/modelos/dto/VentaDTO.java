package com.porempresa.jwt.modelos.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class VentaDTO {

    public String uid;
    public String invoiceAddress;//nuevo
    public Date invoiceDate;
    public int status;
    public int businessId;
    public EmpresaDTO empresa;
    public Boolean applySplit;
    public String currency;
    public BigDecimal totalAmount;
    public Integer totalAmountMSats;
    public Integer totalSats;
    public int orderId;
    public BigDecimal amountSat;
    public Date createdAt;
    public Date updatedAt;
    public boolean distributed;

    public String customerName;
    public String customerEmail;

    public BigDecimal subTotal;
    public BigDecimal taxes;
    public BigDecimal shipping;

    public List<FacturaDetalleDTO> details;//viene definido desde que el endpoint crear invoice es invocado
    public List<VentaDistribucionDto> paymentSplit; //es llenado cuando un invoice es consultado


}


