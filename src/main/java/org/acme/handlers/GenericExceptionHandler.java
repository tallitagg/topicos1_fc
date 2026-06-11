package org.acme.handlers;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorResponseDTO;

//@Provider
public class GenericExceptionHandler implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        int status = 500;
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorResponseDTO.of(status, "INTERNAL_SERVER_ERROR", "Erro interno do servidor"))
                .build();
    }
}