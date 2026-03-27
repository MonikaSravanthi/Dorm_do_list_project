package com.taskmanager.taskmanager.exception;

public class UserErrorResponse {
    private int status;
    private String message;
    private long timestamp;

    public UserErrorResponse(int status, String message, int timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public UserErrorResponse() {

    }

    public void setStatus(int status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public int getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public long getTimestamp() {
        return timestamp;
    }



}
