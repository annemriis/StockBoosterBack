package com.example.back.ito03022021backend.databaseTests;


import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.model.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DatabaseTests {

    private final UsersRepository usersRepository;
    private final UserBuilder userBuilder;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private User user;

    @Autowired
    public DatabaseTests(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.userBuilder = new UserBuilder();
    }


    @Test
    public void testAddingUser() {

        // Create users
        User user1 = new UserBuilder()
                .withId(1L)
                .withName("P")
                .withEmail("p")
                .withPassword("a")
                .build();

        User user2 = new UserBuilder()
                .withId(2L)
                .withName("2")
                .withEmail("2")
                .withPassword("2")
                .build();

        usersRepository.save(user1);
        usersRepository.save(user2);

        User use1 = usersRepository.getById(1L);
        User use2 = usersRepository.findUsersByName("2");

        System.out.printf("%s, %s%n", use1.getName(), user1.getName());

    }

}
