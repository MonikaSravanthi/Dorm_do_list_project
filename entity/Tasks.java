
package com.taskmanager.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="task_id")
    private Integer  taskId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "user_id",nullable = false)
    @JsonIgnore
    private Users  user;


    @Column(name = "task_description")
    private String taskDescription;
    @Column(name = "completed_date")
    private LocalDate completedDate;
    @Column(name = "due_date")
    private LocalDate  dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    public Tasks() {}
    public enum TaskStatus {
        PENDING,
        COMPLETED
    }

    public Tasks(Integer taskId, Users user, String taskDescription, LocalDate completedDate, LocalDate dueDate, TaskStatus  taskStatus) {
        this.taskId = taskId;
        this.user = user;
        this.taskDescription = taskDescription;
        this.completedDate = completedDate;
        this.dueDate = dueDate;
        this.taskStatus = taskStatus;


    }

    public Integer  getTaskId() {
        return taskId;
    }
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

   public Users getUser() {
        return user;
   }
   public void setUser(Users user) {
        this.user = user;
   }
    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public LocalDate getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
