package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository repository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.repository = usersRepository;
    }

    public ResponseEntity<User> createUser(User newUser) {
        User user = new UserBuilder()
                .withName(newUser.getName())
                .withEmail(newUser.getEmail())
                .withPassword(newUser.getPassword())
                .build();
        User _user = repository.save(user);
        return new ResponseEntity<>(_user, HttpStatus.CREATED);

    }

}
