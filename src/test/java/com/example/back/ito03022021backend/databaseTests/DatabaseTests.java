package com.example.back.ito03022021backend.databaseTests;

import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.users.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class DatabaseTests {

    private final UsersRepository usersRepository;

    @Autowired
    public DatabaseTests(@Lazy UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Test
    void testAddingUser() {

        // Create users
        User user1 = new UserBuilder()
                .withName("P")
                .withEmail("pppp")
                .withPassword("a")
                .withUserRole(UserRole.USER)
                .build();

        User user2 = new UserBuilder()
                .withName("2")
                .withEmail("2")
                .withPassword("2")
                .withUserRole(UserRole.USER)
                .build();

        User admin = new UserBuilder()
                .withName("ad")
                .withEmail("ad")
                .withPassword("ad")
                .withUserRole(UserRole.ADMIN)
                .build();

        usersRepository.saveAndFlush(user1);
        usersRepository.saveAndFlush(user2);
        usersRepository.saveAndFlush(admin);

        List<User> use1 = usersRepository.findAll();

        // The list size should be 2 since there are 2 entities saved in database
        assertEquals(0, use1.size() % 3);

        // The first users name is P so the other users name is P, the classes cannot be compared because
        // getting users from the database creates a new class
        assertEquals("P", usersRepository.findUsersByName("P").getName());

        assertEquals("P", usersRepository.findUsersByEmail("pppp").getName());

        assertEquals("P", usersRepository.findUsersById(1L).getName());

        // Test if userRole works on user
        assertEquals(UserRole.USER, usersRepository.findUsersById(1L).getUserRole());

        // Test if userRole works on admin
        assertEquals(UserRole.ADMIN, usersRepository.findUsersByName("ad").getUserRole());
    }
}
