package org.acme.exceptions;

public class BusinessException extends RuntimeException {
    private final int status;
    private final String error;
    public BusinessException(String mensagem) {
        this(mensagem, 400, "BUSINESS_ERROR");
    }
    public BusinessException(String mensagem, int status) {
        this(mensagem, status, "BUSINESS_ERROR");
    }
    public BusinessException(String mensagem, int status, String error) {
        super(mensagem);
        this.status = status;
        this.error = error == null || error.isBlank() ? "BUSINESS_ERROR" : error;
    }
    public int getStatus() {
        return status;
    }
    public String getError() {
        return error;
    }
}