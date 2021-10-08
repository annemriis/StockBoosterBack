package com.example.back.ito03022021backend.databaseTests;


import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.model.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest
public class DatabaseTests {

    private final UsersRepository usersRepository;
    private final UserBuilder userBuilder;

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    public DatabaseTests(@Lazy UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.userBuilder = new UserBuilder();
    }


    @Test
    void testAddingUser() {

        // Create users
        User user1 = new UserBuilder()
                .withName("P")
                .withEmail("pppp")
                .withPassword("a")
                .build();

        User user2 = new UserBuilder()
                .withName("2")
                .withEmail("2")
                .withPassword("2")
                .build();


        usersRepository.saveAndFlush(user1);
        usersRepository.saveAndFlush(user2);

        List<User> use1 = usersRepository.findAll();

        // The list size should be 2 since there are 2 entities saved in database
        assertEquals(2, use1.size());


        // The first users name is P so the other users name is P, the classes cannot be compared because
        // getting users from the database creates a new class
        assertEquals("P", usersRepository.findUsersByName("P").getName());

        assertEquals("P", usersRepository.findUsersByEmail("pppp").getName());

        assertEquals("P", usersRepository.findUsersById(1L).getName());
    }

}