package org.acme.handlers;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorResponseDTO;
import org.acme.exceptions.UnauthorizedException;

//@Provider
public class UnauthorizedExceptionHandler implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException exception) {
        int status = 401;
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorResponseDTO.of(status, "UNAUTHORIZED", exception.getMessage()))
                .build();
    }
}