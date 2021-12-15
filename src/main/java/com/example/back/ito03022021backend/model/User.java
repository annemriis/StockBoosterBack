package com.example.back.ito03022021backend.model;

import com.example.back.ito03022021backend.security.users.UserRole;

import javax.persistence.*;
import java.util.List;

@Entity(name = "User")
@Table(name= "users")
@TableGenerator(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "userRole")
    private UserRole userRole;

    @ElementCollection
    @CollectionTable(name = "_stocks", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "stocks")
    private List<String> stocks;


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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<String> getStocks() {
        return stocks;
    }

    public void setStocks(List<String> stocks) {
        this.stocks = stocks;
    }
}
