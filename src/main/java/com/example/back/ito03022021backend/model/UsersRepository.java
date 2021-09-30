package com.example.back.ito03022021backend.model;


import com.example.back.ito03022021backend.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Lazy
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    User findUsersByName(String name);

    User findUsersByEmail(String email);

    User findUsersById(Long id);
}
