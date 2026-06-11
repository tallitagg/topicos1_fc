package org.acme.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ErrorResponseDTO;

import java.util.ArrayList;
import java.util.List;

//@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {
    public record FieldError(String field, String message) {
    }

    public record ValidationErrorResponse(
            int status,
            String error,
            String message,
            String timestamp,
            List<FieldError> violations
    ) {
    }

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        int status = 400;
        List<FieldError> violations = new ArrayList<>();
        for (ConstraintViolation<?> v : exception.getConstraintViolations()) {
            String field = v.getPropertyPath() == null ? null : v.getPropertyPath().toString();
            violations.add(new FieldError(field, v.getMessage()));
        }
        var base = ErrorResponseDTO.of(status, "VALIDATION_ERROR", "Dados inválidos");
        var body = new ValidationErrorResponse(
                base.status(),
                base.error(),
                base.message(),
                base.timestamp(),
                violations
        );
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }
}