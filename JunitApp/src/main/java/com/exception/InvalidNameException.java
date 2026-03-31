package com.exception;

public class InvalidNameException extends RuntimeException {

    private String message;
    public InvalidNameException(String message) {

        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
