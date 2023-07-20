package com.example.SpringBoot.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "Myusers")
public class User {

    @Id
    @SequenceGenerator(
            name = "userSequence",
            allocationSize = 1,
            sequenceName = "userSequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userSequence"
    )
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;


    public User(){

    }

    public User(String name, String password, String role) {
        this.username = name;
        this.password = password;
        this.role = role;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}