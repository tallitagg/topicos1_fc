package org.acme.handlers;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
    
//@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
       Map<String, Object> response = new HashMap<>();
       Map<String, String> fieldErrors = new HashMap<>();

       for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
           String campo = violation.getPropertyPath().toString();
           fieldErrors.put(campo, violation.getMessage());
       }

       response.put("status", 400);
       response.put("menssagem", "Erro de validação");
       response.put("erros", fieldErrors);

       return Response.status(Response.Status.BAD_REQUEST)
               .type(MediaType.APPLICATION_JSON)
               .entity(response)
               .build();
    }
}
