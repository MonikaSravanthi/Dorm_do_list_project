package com.taskmanager.taskmanager.DTO;

import java.time.LocalDate;

public class TaskUpdateDTO {
    private  String taskDescription;
    private LocalDate dueDate;
    private Integer userId;

    public TaskUpdateDTO() {}

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
