package org.acme.exceptions;

public class EstoqueInsuficienteException extends BusinessException{
    public EstoqueInsuficienteException(String mensagem) {
        super(mensagem, 409, "ESTOQUE_INSUFICIENTE");
    }
}
