package com.porempresa.jwt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SerializarObjeto {
    public SerializarObjeto() {
    }
    public static String serializar(Object model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(model);
        return json;
    }
}