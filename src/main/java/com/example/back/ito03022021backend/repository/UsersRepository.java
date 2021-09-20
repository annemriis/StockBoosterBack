package com.example.back.ito03022021backend.repository;


import com.example.back.ito03022021backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findUsersByName(String name);
}
