package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.models.Role;
import java.util.List;


public interface RoleService {
    List<Role> findAll();

    Role findRoleById(long id);

    Role save(Role role);

    Role findByName(String name);

    void deleteAll();

    Role update(long id,Role role);
}
