package com.taskmanager.taskmanager.DTO;

import com.taskmanager.taskmanager.entity.Tasks;

import java.time.LocalDate;

public class TaskResponseDTO {
    private Integer taskId;
    private String taskDescription;
    private LocalDate completedDate;
    private LocalDate dueDate;
    private String profilePicture;
    private Tasks.TaskStatus taskStatus;

    public Tasks.TaskStatus getTaskStatus() {
        return taskStatus;
    }
public void setTaskStatus(Tasks.TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
}
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    public Integer getTaskId() {
        return taskId;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;

    }
    public LocalDate getCompletedDate() {
        return completedDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
}
