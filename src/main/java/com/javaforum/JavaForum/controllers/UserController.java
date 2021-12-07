package com.javaforum.JavaForum.controllers;

import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Returns a list of all users
    // Link: http://localhost:2019/users/users"
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/users", produces = "application/json")
    public ResponseEntity<?> listAllUsers(){
        List<User> allUsers = userService.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // Returns single user based off id
    // Link: http://localhost:2019/users/user/4
    @GetMapping(value="/user/{userId}", produces = "application/json")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId){
        User user = userService.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Return a user object based on a given username
    // Link: http://localhost:2019/users/user/name/cinnamon
    @GetMapping(value="/user/name/{userName}", produces = "application/json")
    public ResponseEntity<?> getUserByName(@PathVariable String userName) {
        User user = userService.findByName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Returns a list of users whose username contains the given substring
    // Link: http://localhost:2019/users/user/name/like/ci
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/user/name/like/{userName}", produces = "application/json")
    public ResponseEntity<?> getUserLikeName(@PathVariable String userName) {
        List<User> user = userService.findByNameContaining(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Given a complete User Object, create a new User record and accompanying useremail records and user role records.
    // http://localhost:2019/users/user
    // @param newuser A complete new user to add including emails and roles. (role must already exist.)
    // @return A location header with the URI to the newly created user and a status of CREATED
    @PostMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody User newUser) throws URISyntaxException {
        newUser.setUserid(0);
        newUser = userService.save(newUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(newUser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);
        return new ResponseEntity<>(newUser, responseHeaders, HttpStatus.CREATED);
    }

    // Given a complete User Object, Given the user id, primary key, is in the User table,
    // replace the User record and Useremail records. ** Roles are handled through different endpoints **
    // Link: http://localhost:2019/users/user/15
    // @param updateUser - A complete User including
    //      roles to be used to replace the User. Roles must already exist.
    // @param userid     The primary key of the user you wish to replace.
    @PutMapping(value = "/user/{userId}", consumes = "application/json")
    public ResponseEntity<?> updateFullUser(@Valid @RequestBody User updatedUser, @PathVariable long userId) {
        updatedUser.setUserid(userId);
        userService.save(updatedUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Updates the user record associated with the given id with the provided data.
    //       Only the provided fields are affected.
    // Roles are handled through different endpoints
    // Link: http://localhost:2019/users/user/7
    // @param updateUser - An object containing values for just the fields that are being updated.
    //      All other fields are left NULL.
    // @param id - The primary key of the user you wish to update.
    @PatchMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody User updateUser, @PathVariable long id) {
        userService.update(updateUser, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    // Deletes a given user along with associated emails and roles
    // Link: http://localhost:2019/users/user/14
    // @param id - The primary key of the user you wish to delete
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Returns the User record for the currently authenticated user based off of the supplied access token
    // Link: http://localhost:2019/users/getuserinfo
    // @param authentication - The authenticated user object provided by Spring Security
    @ApiOperation(value = "returns the currently authenticated user", response = User.class)
    @GetMapping(value = "/getuserinfo", produces = "application/json")
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication) {
        User user = userService.findByName(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
