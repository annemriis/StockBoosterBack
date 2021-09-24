package com.example.back.ito03022021backend.model;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;
import java.util.ArrayList;

@Entity(name = "User")
@Schema(name = "schema")
@Table(name= "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "stocks")
    @ElementCollection
    private ArrayList<String> stocks;


    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(Long id, String name, String email, String password, ArrayList<String> stocks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.stocks = stocks;
    }

}
