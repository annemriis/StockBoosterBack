package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.builders.UserBuilder;
import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.users.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles("test")
public class MyUserDetailsServiceTest {

    private final MyUserDetailsService myUserDetailsService ;
    private UsersRepository usersRepository;

    @Autowired
    public MyUserDetailsServiceTest(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.myUserDetailsService = new MyUserDetailsService(usersRepository);
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        try {
            myUserDetailsService.loadUserByUsername("Mati123");
            fail("Should have thrown UsernameNotFoundException.");
        } catch (UsernameNotFoundException ignored) {
        }
    }

    @Test
    void testLoadUserByUserNameUserFound() {
        User user = new UserBuilder()
                .withName("Mati")
                .withEmail("mati.ee")
                .withPassword("Mati123")
                .withUserRole(UserRole.USER)
                .build();

        usersRepository.saveAndFlush(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("Mati");

        assertEquals("Mati", userDetails.getUsername());
        assertEquals("Mati123", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUserNameAdminFound() {
        User user = new UserBuilder()
                .withName("Kati")
                .withEmail("kati.ee")
                .withPassword("Kati122")
                .withUserRole(UserRole.ADMIN)
                .build();

        usersRepository.saveAndFlush(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("Kati");

        assertEquals("Kati", userDetails.getUsername());
        assertEquals("Kati122", userDetails.getPassword());
    }
}
