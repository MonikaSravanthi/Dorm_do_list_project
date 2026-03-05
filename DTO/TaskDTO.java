package com.taskmanager.taskmanager.DTO;

import com.taskmanager.taskmanager.entity.Users;

import java.time.LocalDate;

public class TaskDTO {

    private String taskDescription;
    private Integer userId;
     private LocalDate dueDate;


    public TaskDTO( ) {

    }


    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
