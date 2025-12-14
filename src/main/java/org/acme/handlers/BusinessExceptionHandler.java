package org.acme.handlers;

import org.acme.exceptions.BusinessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.*;

@Provider
public class BusinessExceptionHandler implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {

        Map<String, Object> json = new HashMap<>();
        json.put("status", 400);
        json.put("mensagem", e.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(json)
                .build();
    }
}
