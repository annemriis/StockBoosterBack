package com.example.back.ito03022021backend.security.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class ResponseTest {

    @Test
    void testLoginResponse() {
        LoginResponse loginResponse = new LoginResponse("Mati", "12-rb2-53", UserRole.USER);
        assertEquals("Mati", loginResponse.getUsername());
        assertEquals("12-rb2-53", loginResponse.getToken());
        assertEquals(UserRole.USER, loginResponse.getRole());
        loginResponse.setUsername("Mati2000");
        loginResponse.setToken("13-45");
        loginResponse.setRole(UserRole.ADMIN);
        assertEquals("Mati2000", loginResponse.getUsername());
        assertEquals("13-45", loginResponse.getToken());
        assertEquals(UserRole.ADMIN, loginResponse.getRole());
    }
}
