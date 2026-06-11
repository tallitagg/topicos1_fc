package org.acme.handlers;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorResponseDTO;
import org.acme.exceptions.BusinessException;

//@Provider
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {
    @Override
    public Response toResponse(BusinessException exception) {
        int status = exception.getStatus();
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorResponseDTO.of(status, exception.getError(), exception.getMessage()))
                .build();
    }
}