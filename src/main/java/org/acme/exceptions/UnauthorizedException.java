package org.acme.exceptions;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(String mensagem) {
        super(mensagem, 401, "UNAUTHORIZED");
    }
}
