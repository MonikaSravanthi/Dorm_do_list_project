package com.taskmanager.taskmanager.exception;


public class TaskErrorResponse {

    private Integer status;
    private String errorMessage;

    public TaskErrorResponse(Integer status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
