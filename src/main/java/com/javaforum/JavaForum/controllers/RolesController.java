package com.javaforum.JavaForum.controllers;

import com.javaforum.JavaForum.models.Role;
import com.javaforum.JavaForum.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    RoleService roleService;

    // List of all roles
    // Link: http://localhost:2019/roles/roles
    @GetMapping(value = "/roles", produces = "application/json")
    public ResponseEntity<?> listRoles() {
        List<Role> allRoles = roleService.findAll();
        return new ResponseEntity<>(allRoles, HttpStatus.OK);
    }

    // The Role referenced by the given primary key
    // Link: http://localhost:2019/roles/role/3
    // @param roleId - The primary key (long) of the role you seek
    @GetMapping(value = "/role/{roleId}", produces = "application/json")
    public ResponseEntity<?> getRoleById(@PathVariable Long roleId) {
        Role role = roleService.findRoleById(roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    // The Role with the given name
    // Link: http://localhost:2019/roles/role/name/data
    // @param roleName - The name of the role you seek
    @GetMapping(value = "/role/name/{roleName}", produces = "application/json")
    public ResponseEntity<?> getRoleByName(@PathVariable String roleName) {
        Role role = roleService.findByName(roleName);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    // Given a complete Role object, create a new Role record
    // Link: http://localhost:2019/roles/role
    // @param newRole - A complete new Role object
    @PostMapping(value = "/role", consumes = "application/json")
    public ResponseEntity<?> addNewRole( @RequestBody Role newRole) {
        newRole.setRoleid(0);
        newRole = roleService.save(newRole);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRoleURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{roleid}").buildAndExpand(newRole.getRoleid()).toUri();
        responseHeaders.setLocation(newRoleURI);
        return new ResponseEntity<>( responseHeaders, HttpStatus.CREATED);
    }

    // The process allows you to update a role name only!
    // Link: http://localhost:2019/roles/role/3
    // @param roleid - The primary key (long) of the role you wish to update
    // @param newRole - The new name (String) for the role
    @PutMapping(value = "/role/{roleid}", consumes = {"application/json"})
    public ResponseEntity<?> updateRole(@PathVariable long roleid, @RequestBody Role newRole) {
        newRole = roleService.update(roleid, newRole);
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }
}
