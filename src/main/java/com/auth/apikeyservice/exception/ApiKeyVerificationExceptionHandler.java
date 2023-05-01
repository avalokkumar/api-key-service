package com.auth.apikeyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@ControllerAdvice
public class ApiKeyVerificationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidAPIKeyException.class)
    protected ResponseEntity<Object> handleInvalidAPIKeyException(InvalidAPIKeyException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(NoSuchAlgorithmException.class)
    protected ResponseEntity<Object> handleNoSuchAlgorithmException(NoSuchAlgorithmException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred");
    }

    @ExceptionHandler(InvalidKeyException.class)
    protected ResponseEntity<Object> handleInvalidKeyException(InvalidKeyException ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(status.value(), message);
        return ResponseEntity.status(status).body(errorResponse);
    }
    
    private static class ApiErrorResponse {
        private int status;
        private String message;

        public ApiErrorResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
