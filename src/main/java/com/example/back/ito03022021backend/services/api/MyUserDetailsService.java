package com.example.back.ito03022021backend.services.api;

import com.example.back.ito03022021backend.model.User;
import com.example.back.ito03022021backend.repositories.UsersRepository;
import com.example.back.ito03022021backend.security.users.MyUser;
import com.example.back.ito03022021backend.security.users.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UsersRepository usersRepository;

    @Autowired
    public MyUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = usersRepository.findAllByName(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        User user = users.get(0);

        return new MyUser(user.getName(), user.getPassword(), toAuthorities(user), user.getUserRole());
    }

    private List<SimpleGrantedAuthority> toAuthorities(User user) {
            return getRoles(user.getUserRole())
                    .map(UserRole::toApplicationRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
    }

    private Stream<UserRole> getRoles(UserRole userRole) {
        if (userRole.isAdmin()) {
            return Arrays.stream((UserRole.values()));
        }
        return Stream.of(userRole);
    }
}
