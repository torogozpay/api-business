package com.porempresa.jwt.util;

import com.porempresa.jwt.modelos.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponsEntityUtil {
    public static ResponseEntity<ApiResponse> responseApiOk(String message, Object data, Boolean success) {
        ApiResponse response = new ApiResponse();
        response.setData(data);
        response.setMessage(message);
        response.setSuccess(success);
        return ResponseEntity.ok().body(response);
    }

    public static ResponseEntity<ApiResponse> responseApi201(String message, Object data, Boolean success) {
        ApiResponse response = new ApiResponse();
        response.setData(data);
        response.setMessage(message);
        response.setSuccess(success);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static ResponseEntity<ApiResponse> responseApi(String message, Object data, Boolean success,
                                                                         HttpStatus status) {
        ApiResponse response = new ApiResponse();
        response.setData(data);
        response.setMessage(message);
        response.setSuccess(success);
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<ApiResponse> responseApi401() {
        ApiResponse response = new ApiResponse();
        response.setData(null);
        response.setMessage("Token no Valido");
        response.setSuccess(false);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    public static ResponseEntity<ApiResponse> responseApi400(String message, Object data, Boolean success,
                                                                            HttpStatus status) {
        ApiResponse response = new ApiResponse();
        response.setData(data);
        response.setMessage(message);
        response.setSuccess(false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
