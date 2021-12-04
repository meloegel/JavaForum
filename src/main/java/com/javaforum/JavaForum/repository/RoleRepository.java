package com.javaforum.JavaForum.repository;

import com.javaforum.JavaForum.models.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByNameIgnoreCase(String name);

    @Transactional
    @Modifying
    @Query(value = "UPDATE roles SET name = :name, lastmodifiedby = :username, lastmodifieddate = CURRENT_TIMESTAMP WHERE roleid = :roleid", nativeQuery = true)
    void updateRoleName(String username, long roleid, String name);
}

