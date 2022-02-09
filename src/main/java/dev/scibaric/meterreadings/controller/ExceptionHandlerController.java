package dev.scibaric.meterreadings.controller;

import dev.scibaric.meterreadings.exception.ApiExceptionResponse;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Class for handling runtime exceptions and wrapping them in {@link ApiExceptionResponse} and at last wrapping it
 * in {@link ResponseEntity<ApiExceptionResponse>} to be presented to the user of the REST API.
 */
@RestControllerAdvice
public class ExceptionHandlerController {


    /**
     * Method called when controller throws {@link ResourceNotFoundException}. Exception is wrapped in
     * {@link ApiExceptionResponse} and then it is wrapped in {@link ResponseEntity<ApiExceptionResponse>} with
     * 404 Not found http status. Exception message is stored in a {@link ApiExceptionResponse} message field.
     *
     * @param ex ResourceNotFoundException
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiExceptionResponse> handleExceptions(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    /**
     * Method called when controller throws {@link IllegalArgumentException}. Exception is wrapped in
     * {@link ApiExceptionResponse} and then it is wrapped in {@link ResponseEntity<ApiExceptionResponse>} with
     * 400 Bad request http status. Exception message is stored in a {@link ApiExceptionResponse} message field.
     *
     * @param ex IllegalArgumentException
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiExceptionResponse> handleExceptions(IllegalArgumentException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ApiExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    /**
     * Method called when controller throws {@link RuntimeException}. Exception is wrapped in
     * {@link ApiExceptionResponse} and then it is wrapped in {@link ResponseEntity<ApiExceptionResponse>} with
     * 500 Internal server error http status. Message presented to the user is <i>Service error</i>
     *
     * @param ex RuntimeException
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiExceptionResponse> handleExceptions(RuntimeException ex) {
        return ResponseEntity
                .internalServerError()
                .body(new ApiExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Service error"));
    }
}
