package com.example.back.ito03022021backend.contorllers;


import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/users")
@RestController
public class UserController {

    private final UsersRepository repository;

    @Autowired
    public UserController(UsersRepository usersRepository) {
        this.repository = usersRepository;
    }

    @PostMapping(path = "/user",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User user = new UserBuilder()
                .withName(newUser.getName())
                .withEmail(newUser.getEmail())
                .withPassword(newUser.getPassword())
                .build();
        User _user = repository.save(user);
        return new ResponseEntity<>(_user, HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/{name}")
    public User getUserByName(@PathVariable String name) {
        return repository.findUsersByName(name);
    }
}
