package org.acme.handlers;

import org.acme.exceptions.RecursoNaoEncontradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

//@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<RecursoNaoEncontradoException> {

    @Override
    public Response toResponse(RecursoNaoEncontradoException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 404);
        body.put("menssagem", ex.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(body)
                .build();
    }
    
}
