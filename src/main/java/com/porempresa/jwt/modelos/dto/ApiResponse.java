package com.porempresa.jwt.modelos.dto;

public class ApiResponse {

    private Boolean success;
    private String message;
    private Object data;

    public ApiResponse() {
        super();
    }



    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(Boolean success) {
        this.success = success;
    }

    public ApiResponse(String message) {
        this.message = message;
    }



    public ApiResponse(Boolean success, String message, Object data) {
        super();
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
