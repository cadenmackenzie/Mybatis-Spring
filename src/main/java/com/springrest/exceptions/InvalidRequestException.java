package com.springrest.exceptions;

public class InvalidRequestException extends Exception {

    String message;
    int status_code;

    public InvalidRequestException(String message, int status_code) {
        this.message = message;
        this.status_code = status_code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }
}
