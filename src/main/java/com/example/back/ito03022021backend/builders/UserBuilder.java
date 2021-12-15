package com.example.back.ito03022021backend.builders;

import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.security.users.UserRole;

public class UserBuilder {


    private String email;
    private String password;
    private String name;


    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }


    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        User user = new User();
        user.setEmail(this.email);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setUserRole(UserRole.USER);
        return user;
    }

}
