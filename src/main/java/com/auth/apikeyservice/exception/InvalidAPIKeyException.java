package com.auth.apikeyservice.exception;

public class InvalidAPIKeyException extends RuntimeException{
    public InvalidAPIKeyException() {
        super();
    }

    public InvalidAPIKeyException(String message) {
        super(message);
    }
}
