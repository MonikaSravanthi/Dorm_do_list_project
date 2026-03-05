package com.taskmanager.taskmanager.exception;


public class taskNotFoundException extends RuntimeException {
    public taskNotFoundException(String message) {
        super(message);
    }

    public taskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
