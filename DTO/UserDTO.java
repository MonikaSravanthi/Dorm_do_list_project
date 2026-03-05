package com.taskmanager.taskmanager.DTO;

public class UserDTO {
    private int userId;
    private String firstName;

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }
}
