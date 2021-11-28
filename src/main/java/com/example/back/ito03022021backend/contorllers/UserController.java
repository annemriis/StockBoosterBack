package com.example.back.ito03022021backend.contorllers;


import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.UserUtil;
import com.example.back.ito03022021backend.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/users")
@RestController
public class UserController {

    private final UsersRepository repository;
    private final UserService userService;

    @Autowired
    public UserController(UsersRepository usersRepository, UserService userService) {
        this.repository = usersRepository;
        this.userService = userService;
    }

    @PostMapping(path = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping(path = "/user/{name}")
    public User getUserByName(@PathVariable String name) {
        return repository.findUsersByName(name);
    }

    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable long id) {
        return repository.findUsersById(id);
    }


    @GetMapping("me")
    public Object me() {
        return UserUtil.getLoggedInUser();
    }

    @GetMapping("admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("user")
    public String user() {
        return "user";
    }

}
