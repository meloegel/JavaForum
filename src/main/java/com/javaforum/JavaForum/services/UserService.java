package com.javaforum.JavaForum.services;


import com.javaforum.JavaForum.models.User;
import java.util.List;


public interface UserService {
    List<User> findAll();

    List<User> findByNameContaining(String username);

    User findUserById(long id);

    User findByName(String username);

    void delete(long id);

    User save(User user);

    User update(User user, long id);

    void deleteAll();
}
