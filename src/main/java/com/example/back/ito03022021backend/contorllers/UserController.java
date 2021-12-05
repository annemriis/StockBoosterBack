package com.example.back.ito03022021backend.contorllers;


import com.example.back.ito03022021backend.security.jwt.JwtTokenProvider;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.ApplicationRoles;
import com.example.back.ito03022021backend.security.UserUtil;
import com.example.back.ito03022021backend.security.users.LoginRequest;
import com.example.back.ito03022021backend.security.users.LoginResponse;
import com.example.back.ito03022021backend.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RequestMapping({"/users", "/users2"})
@RestController
public class UserController {

    private final UsersRepository repository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UsersRepository usersRepository, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.repository = usersRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
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

    @Secured(ApplicationRoles.ADMIN)
    @GetMapping("admin")
    public String admin() {
        return "admin";
    }

    @Secured(ApplicationRoles.USER)
    @GetMapping("user")
    public String user() {
        return "user";
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
