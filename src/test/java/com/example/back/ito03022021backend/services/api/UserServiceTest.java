package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.jwt.JwtTokenProvider;
import com.example.back.ito03022021backend.security.users.LoginRequest;
import com.example.back.ito03022021backend.security.users.LoginResponse;
import com.example.back.ito03022021backend.security.users.RegisterRequest;
import com.example.back.ito03022021backend.security.users.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    private final UserService userService;
    private final UsersRepository usersRepository;

    @Autowired
    public UserServiceTest(UsersRepository usersRepository, JwtTokenProvider jwtTokenProvider,
                           AuthenticationManager authenticationManager) {
        this.usersRepository = usersRepository;
        this.userService = new UserService(usersRepository, jwtTokenProvider, authenticationManager);
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Kati2000");
        registerRequest.setPassword("Kati123");

        userService.register(registerRequest);
        User user = usersRepository.findUsersByName("Kati2000");
        assertEquals("Kati2000", user.getName());
        assertEquals(UserRole.USER, user.getUserRole());
    }

    @Test
    void testLoginWithValidInformation() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Mati20");
        registerRequest.setPassword("Mati1234");
        userService.register(registerRequest);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Mati20");
        loginRequest.setPassword("Mati1234");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertEquals("Mati20", loginResponse.getUsername());
        assertEquals(UserRole.USER, loginResponse.getRole());
    }

    @Test
    void testLoginWithEmptyLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("");
        assertNull(userService.login(loginRequest));
    }
}
