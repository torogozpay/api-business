package com.porempresa.jwt.cliente;

import com.porempresa.jwt.util.SerializarObjeto;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public abstract class ManejadorPeticiones {
    public ManejadorPeticiones(){

    }
    public <T> Map<String, String> construirPeticionSincronaPOST(T objetoSerializar, String endpoint, String contentType, @Nullable String token) throws IOException, InterruptedException {
        /**
         * keys: token, body y status
         */
        Map<String, String> mapDatosRespuesta = new HashMap<>();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();

        HttpRequest request = null;
        // Si el token no es null, entonces lo mandamos en el encabezado de la solicitud
        if(token!=null) {
             request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(25))
                    .headers("Content-Type", contentType, "Authorization", "Bearer " + token)
                    .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                    .build();
        }else{ // Sino solo mandamos el Content-Type en el encabezado de la solicitud
            request = HttpRequest.newBuilder()
            .uri(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(25))
                    .header("Content-Type", contentType)
                    .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                    .build();
        }

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();

        System.out.println("Status Code " + statusCode + "\n" + "Body" + body);
        mapDatosRespuesta.put("body", body);
        mapDatosRespuesta.put("status", String.valueOf(statusCode));
        return mapDatosRespuesta;
    }

    /**
     * Al extraer el token se entiende que este método realizará una petición POST
     */
    public <T> Map<String, String> extraerTokenDeSolicitud(T objetoSerializar, String endpoint, String contentType) throws IOException, InterruptedException {
        Map<String, String> mapDatosRespuesta = new HashMap<>();

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();

        if(statusCode == 200) {
            String[] parts = body.split(":");
            String jwt = parts[1].replaceAll("\"", "").trim();
            if (jwt.endsWith("}")) {
                jwt = jwt.substring(0, jwt.length() - 1);
            }
            System.out.println("JWT: " + jwt);

            mapDatosRespuesta.put("token", jwt);
            mapDatosRespuesta.put("body", body);
            mapDatosRespuesta.put("status", String.valueOf(statusCode));
        }
        else{
            System.out.println("No se pudo extraer el token, ocurrió un error");
            mapDatosRespuesta.put("error", "No se pudo extraer el token, ocurrió un error");
            // Le pasamos la cadena null para evitar algún NullPointerException
            mapDatosRespuesta.put("status", "null");
        }
        return mapDatosRespuesta;
    }
    /*public <T> void construirPeticionAsincrona(T objetoSerializar, String endpoint, String contentType, String token) throws IOException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .headers("Content-Type", contentType, "Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        CompletableFuture solicitudAsync = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("StatusCode: " + response.statusCode());
                    System.out.println("Body: " + response.body());
                    return response;
                })
                .thenAccept(res -> {

                });
    }
    public <T> void construirPeticionAsincrona(T objetoSerializar, String endpoint, String contentType) throws IOException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        CompletableFuture solicitudAsync = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    System.out.println("StatusCode: " + response.statusCode());
                    System.out.println("Body: " + response.body());
                    return response;
                })
                .thenAccept(res -> {

                });
    }
    public <T> void construirPeticionSincrona(T objetoSerializar, String endpoint, String contentType, String token) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .headers("Content-Type", contentType, "Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Body: " + body);
    }
    public <T> HttpResponse<?> construirPeticionSincrona(T objetoSerializar, String endpoint, String contentType) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Body: " + body);
        return response;
    }
    public <T> HttpResponse<?> construirPeticionSincronaConHttpV1(T objetoSerializar, String endpoint, String contentType) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Body: " + body);
        return response;
    }
    public <T> HttpResponse<?> construirPeticionSincronaConHttpV1(String endpoint, String contentType) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .headers("Content-Type", contentType, "Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();

        System.out.println("Status Code: " + statusCode);
        System.out.println("Body: " + body);
        return response;
    }
    public <T> String construirPeticionSincronaObtenerTokenConHttpV1(T objetoSerializar, String endpoint, String contentType) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(25))
                .build();


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .timeout(Duration.ofSeconds(25))
                .header("Content-Type", contentType)
                .POST(HttpRequest.BodyPublishers.ofString(SerializarObjeto.serializar(objetoSerializar)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        String body = response.body();
        System.out.println("Status Code " + statusCode + "\n" + "Body" + body);
        String[] parts = body.split(":");
        String jwt = parts[1].replaceAll("\"", "").trim();
        if (jwt.endsWith("}")) {
            jwt = jwt.substring(0, jwt.length() - 1);
        }
        System.out.println("JWT: " + jwt);
        return jwt;
    }*/
}