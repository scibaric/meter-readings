package dev.scibaric.meterreadings.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Response class for wrapping <i>RuntimeExceptions</i> and then presenting messages and http statuses in
 * human-readable form.
 */
public class ApiExceptionResponse {
    private HttpStatus status;
    private String message;
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
