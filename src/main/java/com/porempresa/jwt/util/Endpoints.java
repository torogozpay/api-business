package com.porempresa.jwt.util;

public class Endpoints {
    /*private static final String url_base = "https://graph.facebook.com/v16.0/104161472696406/messages";*/
    /// URL base
    private static final String BASE_URL = "http://localhost:8080";
    private static final String contentType = "application/json";

    /**
     * Endpoints de WhatsApp
     **/
    private static final String URL_NAME = "https://graph.facebook.com/v16.0/104161472696406/messages";
    private static final String WHATSAPP_URL_SEND_FOR_URL = "https://graph.facebook.com/v17.0/104161472696406/messages";

    /**
     * Endpoints de Hacienda
     */
    private static final String BASE_URL_HACIENDA = "https://apitest.dtes.mh.gob.sv";
    private static final String AUTORIZACION_URL = BASE_URL_HACIENDA + "/seguridad/auth";
    private static final String RECEPCION_URL = BASE_URL_HACIENDA + "/fesv/recepciondte";
    private static final String CONSULTA_URL = BASE_URL_HACIENDA + "/recepcion/consultadte";
    private static final String CONTINGENCIA_URL = BASE_URL_HACIENDA + "/fesv/contingencia";

    /**
     * Endpoints de Firmador
     */
    private static final String BASE_URL_FIRMADOR = "http://localhost:8113/firma";
    private static final String FIRMAR_DOCUMENTO = BASE_URL_FIRMADOR + "/firmardocumento/";
    private static final String STATUS_FIRMADOR = BASE_URL_FIRMADOR + "/status";

    /*public static String getUrl_base() {
        return url_base;
    }*/

    /**
     * WhatsApp
     */
    public static String getUrlName() {
        return URL_NAME;
    }

    public static String getWhatsappUrlSendForUrl() {
        return WHATSAPP_URL_SEND_FOR_URL;
    }

    public static String getContentType() {
        return contentType;
    }
    /**
     * Hacienda
     */
    public static String getRecepcionUrl() {
        return RECEPCION_URL;
    }
    public static String getConsultaUrl() {
        return CONSULTA_URL;
    }
    public static String getContingenciaUrl() {
        return CONTINGENCIA_URL;
    }

    public static String getAutorizacionUrl() {
        return AUTORIZACION_URL;
    }

    /**
     * Firmador
     */
    public static String getFirmarDocumento() {
        return FIRMAR_DOCUMENTO;
    }

    public static String getStatusFirmador() {
        return STATUS_FIRMADOR;
    }
}
