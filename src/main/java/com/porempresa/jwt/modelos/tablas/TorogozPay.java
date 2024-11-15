package com.porempresa.jwt.modelos.tablas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="torogoz_pay")
@Setter
@Getter
public class TorogozPay implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(unique=true, nullable=false)
    private int id;

    @Column(name = "ln_address")
    public String lnAddress;
}
