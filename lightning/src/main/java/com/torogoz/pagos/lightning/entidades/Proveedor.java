package com.torogoz.pagos.lightning.entidades;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String apellidos;
    private String bitcoinAddress;
    private String lightningAddress;
    private String walletAlternativo;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getBitcoinAddress() {
        return bitcoinAddress;
    }

    public void setBitcoinAddress(String bitcoinAddress) {
        this.bitcoinAddress = bitcoinAddress;
    }

    public String getLightningAddress() {
        return lightningAddress;
    }

    public void setLightningAddress(String lightningAddress) {
        this.lightningAddress = lightningAddress;
    }

    public String getWalletAlternativo() {
        return walletAlternativo;
    }

    public void setWalletAlternativo(String walletAlternativo) {
        this.walletAlternativo = walletAlternativo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
