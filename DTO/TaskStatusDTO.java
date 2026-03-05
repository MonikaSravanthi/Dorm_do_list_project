package com.taskmanager.taskmanager.DTO;

import com.taskmanager.taskmanager.entity.Tasks;

import java.time.LocalDate;

public class TaskStatusDTO {
    private LocalDate completedDate;
    private Tasks.TaskStatus taskStatus;

    public TaskStatusDTO() {}

    public Tasks.TaskStatus getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(Tasks.TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
    public LocalDate getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }
}
