package org.acme.dto;
import java.time.OffsetDateTime;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        String timestamp
) {
    public static ErrorResponseDTO of(int status, String error, String message) {
        return new ErrorResponseDTO(
                status,
                error,
                message,
                OffsetDateTime.now().toString()
        );
    }
}