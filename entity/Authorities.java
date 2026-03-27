package com.taskmanager.taskmanager.entity;

import jakarta.persistence.*;


@Entity
@Table(name="authorities")
public class Authorities {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username",referencedColumnName = "username",nullable = false)
    private Users user;

    @Column(name="authority")
    private String authority;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public void setUser(Users user) {
        this.user = user;
    }
    public Users getUser() {
        return user;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    public String getAuthority() {
        return authority;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
