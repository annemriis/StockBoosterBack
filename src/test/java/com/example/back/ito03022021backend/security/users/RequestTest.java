package com.example.back.ito03022021backend.security.users;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class RequestTest {

    @Test
    void testAddStockRequest() {
        AddStockRequest addStockRequest = new AddStockRequest();
        addStockRequest.setName("Stock");
        addStockRequest.setSymbol("TSLA");
        assertEquals("Stock", addStockRequest.getName());
        assertEquals("TSLA", addStockRequest.getSymbol());
    }

    @Test
    void testLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Mati");
        loginRequest.setPassword("Kati123");
        assertEquals("Mati", loginRequest.getUsername());
        assertEquals("Kati123", loginRequest.getPassword());
    }

    @Test
    void testRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Kati");
        registerRequest.setPassword("Kati1234");
        assertEquals("Kati", registerRequest.getUsername());
        assertEquals("Kati1234", registerRequest.getPassword());
    }
}
