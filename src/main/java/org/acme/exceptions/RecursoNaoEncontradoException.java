package org.acme.exceptions;

public class RecursoNaoEncontradoException extends BusinessException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem, 404, "NOT_FOUND");
    }
}