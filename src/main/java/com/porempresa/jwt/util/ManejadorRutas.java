package com.porempresa.jwt.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ManejadorRutas {
    public ManejadorRutas() {
    }

    public String obtenerKey(String archivoClassPath, String llave) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(archivoClassPath);
        properties.load(inputStream);
        String llaveEncontrada = properties.getProperty(llave);
        return llaveEncontrada;
    }
}
