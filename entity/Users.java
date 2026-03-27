package com.taskmanager.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;


    @Column(name="password")
    private String password;

    @Column(name="profile_pic")
    private String avatarUrl;

    @OneToMany(mappedBy = "user")
    private List<Tasks> tasks;

    @OneToMany(mappedBy="user")

     private List<Authorities> authorities;

    public Users(String userName, String firstName, String lastName , String password, String avatarUrl, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
         this.password = password;
        this.avatarUrl = avatarUrl;
        this.username = userName;

    }

    public Users() {

    }

    public List<Authorities> getAuthorities() {
        List<Authorities> authorities1 = authorities;
        return authorities1;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUserName() {
        return username;
    }

}


