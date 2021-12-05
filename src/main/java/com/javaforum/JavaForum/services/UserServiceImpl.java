package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.exceptions.ResourceNotFoundException;
import com.javaforum.JavaForum.models.Role;
import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.models.UserRoles;
import com.javaforum.JavaForum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    @Autowired
    private RoleService roleService;

    public User findUserById(long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findByNameContaining(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username.toLowerCase());
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User id " + id + " not found!"));
        userRepository.deleteById(id);
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByUsername(name.toLowerCase());
        if (user == null) {
            throw new ResourceNotFoundException("User name " + name + " not found!");
        }
        return user;
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();
        if (user.getUserid() != 0) {
            userRepository.findById(user.getUserid())
                    .orElseThrow(() -> new ResourceNotFoundException("User id " + user.getUserid() + " not found!"));
            newUser.setUserid(user.getUserid());
        }
        newUser.setUsername(user.getUsername().toLowerCase());
        newUser.setPasswordNoEncrypt(user.getPassword());
        newUser.setEmail(user.getEmail().toLowerCase());

        newUser.getRoles().clear();
        for (UserRoles ur : user.getRoles()) {
            Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
            newUser.getRoles().add(new UserRoles(newUser, addRole));
        }
        return userRepository.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) {
        User currentUser = findUserById(id);
        if (helperFunctions.isAuthorizedToMakeChange(currentUser.getUsername())) {
            if (user.getUsername() != null) {
                currentUser.setUsername(user.getUsername()
                        .toLowerCase());
            }
            if (user.getPassword() != null) {
                currentUser.setPasswordNoEncrypt(user.getPassword());
            }
            if (user.getEmail() != null) {
                currentUser.setEmail(user.getEmail().toLowerCase());
            }
            if (user.getRoles().size() > 0) {
                currentUser.getRoles().clear();
                for (UserRoles ur : user.getRoles()) {
                    Role addRole = roleService.findRoleById(ur.getRole().getRoleid());
                    currentUser.getRoles().add(new UserRoles(currentUser, addRole));
                }
            }
            return userRepository.save(currentUser);
        } else {
            throw new OAuth2AccessDeniedException();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
