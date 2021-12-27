package com.example.back.ito03022021backend.controllers;

import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.ApplicationRoles;
import com.example.back.ito03022021backend.security.UserUtil;
import com.example.back.ito03022021backend.security.users.AddStockRequest;
import com.example.back.ito03022021backend.security.users.LoginRequest;
import com.example.back.ito03022021backend.security.users.LoginResponse;
import com.example.back.ito03022021backend.security.users.RegisterRequest;
import com.example.back.ito03022021backend.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping({"/users", "/users2"})
@RestController
public class UserController {

    private final UsersRepository repository;
    private final UserService userService;

    @Autowired
    public UserController(UsersRepository usersRepository, UserService userService) {
        this.repository = usersRepository;
        this.userService = userService;
    }


    @PostMapping(path = "register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Secured(ApplicationRoles.USER)
    @PostMapping(path = "/user/addstock")
    public ResponseEntity<Void> addStockToUser(@RequestBody AddStockRequest request){
        userService.addStockToUser(request.getSymbol(), request.getName());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Secured(ApplicationRoles.USER)
    @GetMapping(path = "/user/stocks/{name}")
    public List<String> getUserStocks(@PathVariable String name) {
        return repository.findUsersByName(name).getStocks();
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

    @PostMapping("login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

}
