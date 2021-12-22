package com.example.back.ito03022021backend.repositories;

import com.example.back.ito03022021backend.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Lazy
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    User findUsersByName(String name);

    User findUsersByEmail(String email);

    User findUsersById(Long id);

    List<User> findAllByName(String name);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.stocks = :stocks where u.name = :name")
    void updateStocks(@Param("stocks") List<String> stocks, @Param("name") String name);


}
