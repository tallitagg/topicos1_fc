package org.acme.handlers;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorResponseDTO;
import org.acme.exceptions.RecursoNaoEncontradoException;
//@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<RecursoNaoEncontradoException> {
    @Override
    public Response toResponse(RecursoNaoEncontradoException exception) {
        int status = 404;
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(ErrorResponseDTO.of(status, "NOT_FOUND", exception.getMessage()))
                .build();
    }
}