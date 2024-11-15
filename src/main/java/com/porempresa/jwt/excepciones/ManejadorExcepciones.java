package com.porempresa.jwt.excepciones;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

public class ManejadorExcepciones {
    private static String excepcion;
    private static Integer codigoHttp;
    public ManejadorExcepciones() {
    }
    public void setLanzadorExcepciones(String excepcion, Integer codigoHttp){
        this.excepcion = excepcion;
        this.codigoHttp = codigoHttp;
    }
    public static ResponseEntity<?> getLanzadorExcepciones(){
        return ResponseEntity.status(codigoHttp).body(excepcion);
    }
}
