package dev.scibaric.meterreadings.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Response class for wrapping <i>RuntimeExceptions</i> and then presenting messages and http statuses in
 * human-readable form.
 */
@Schema
public class ApiExceptionResponse {

    @Schema(description = "Http status", example = "200 OK")
    private HttpStatus status;
    @Schema(description = "Message thrown by exception", example = "Meter reading with id 10 does not exist")
    private String message;
    @Schema(description = "Time of response", example = "2022-02-06T10:56:31.3376027")
    private LocalDateTime time;

    public ApiExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
