package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.security.jwt.JwtTokenProvider;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.users.LoginRequest;
import com.example.back.ito03022021backend.security.users.LoginResponse;
import com.example.back.ito03022021backend.security.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UsersRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserService(UsersRepository usersRepository, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.repository = usersRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
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

    public LoginResponse login(LoginRequest loginRequest) {
        if (!(loginRequest.getUsername()).isBlank() && !loginRequest.getPassword().isBlank()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetails principle = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(principle.getUsername());
            return new LoginResponse(principle.getUsername(), token, UserRole.USER);
        } else {
            // should be exception
            return null;
        }
    }

}
