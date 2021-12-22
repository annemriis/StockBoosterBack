package com.example.back.ito03022021backend.security.users;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUser extends User {
    private  UserRole userRole;

    public MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserRole userRole) {
        super(username, password, authorities);
        this.userRole = userRole;
    }
}
