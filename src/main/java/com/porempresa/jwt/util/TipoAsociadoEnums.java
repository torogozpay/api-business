package com.porempresa.jwt.util;

public enum TipoAsociadoEnums {
    COMERCIO, COLABORADOR, TOROGOZ;

    public String getValue(){
        switch (this){
            case COMERCIO -> {
                return "comercio";
            }
            case COLABORADOR -> {
                return "colaborador";
            }
            case TOROGOZ -> {
                return "torogoz";
            }
            default -> {
                return "";
            }
        }
    }
}
