package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.security.ApplicationRoles;
import com.example.back.ito03022021backend.security.jwt.JwtTokenProvider;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.users.LoginRequest;
import com.example.back.ito03022021backend.security.users.LoginResponse;
import com.example.back.ito03022021backend.security.users.RegisterRequest;
import com.example.back.ito03022021backend.security.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UsersRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UsersRepository usersRepository, JwtTokenProvider jwtTokenProvider,
                       AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.repository = usersRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequest registerRequest) {
        // save new user to database
        if (!(registerRequest.getUsername()).isBlank() && !registerRequest.getPassword().isBlank()) {
            User user = new UserBuilder()
                    .withPassword(passwordEncoder.encode(registerRequest.getPassword()))
                    .withEmail("")
                    .withName(registerRequest.getUsername())
                    .withUserRole(UserRole.USER)
                    .build();
            repository.save(user);
            }
        }



    public LoginResponse login(LoginRequest loginRequest) {
        if (!(loginRequest.getUsername()).isBlank() && !loginRequest.getPassword().isBlank()) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()));
            UserDetails principle = (UserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(principle.getUsername());
            return new LoginResponse(principle.getUsername(), token, getRole(principle));
        } else {
            // should be exception
            return null;
        }
    }

    /**
     * User can either have user or admin role, this determines which role user has
     * @param principle of the given user
     * @return Role of the user
     */
    private UserRole getRole(UserDetails principle) {
        boolean isAdmin = principle.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(ApplicationRoles.ADMIN));
        if (isAdmin) {
            return UserRole.ADMIN;
        }
        return UserRole.USER;
    }

}
