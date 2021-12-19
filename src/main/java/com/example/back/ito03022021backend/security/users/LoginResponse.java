package com.example.back.ito03022021backend.security.users;


public class LoginResponse {

    private String username;
    private String token;
    private UserRole role;

    public LoginResponse(String username, String token, UserRole userRole) {
        this.username = username;
        this.token = token;
        this.role = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
