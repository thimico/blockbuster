package com.example.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return _doHandle(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex, WebRequest request) {
        return _doHandle(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        return _doHandle(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable ex, WebRequest request) {
        return _doHandle(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    private ResponseEntity<ErrorResponse> _doHandle(HttpStatus status, Throwable cause) {
        return _doHandle(cause.getMessage(), status, cause);
    }

    private ResponseEntity<ErrorResponse> _doHandle(String message, HttpStatus status, Throwable cause) {
        String code = status.getReasonPhrase();
        String description = cause.getClass().getSimpleName();
        ErrorResponse errorResponse = new ErrorResponse(code, description, message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
