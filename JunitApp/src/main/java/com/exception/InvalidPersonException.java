package com.exception;

public class InvalidPersonException extends RuntimeException{
    private String message;

    public InvalidPersonException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
