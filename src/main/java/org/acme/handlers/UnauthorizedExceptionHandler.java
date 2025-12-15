package org.acme.handlers;

import org.acme.exceptions.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.*;

//@Provider
public class UnauthorizedExceptionHandler implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException e) {

        Map<String, Object> json = new HashMap<>();
        json.put("status", 401);
        json.put("mensagem", e.getMessage());

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(json)
                .build();
    }
}
