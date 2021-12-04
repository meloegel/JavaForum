package com.javaforum.JavaForum.services;

import com.javaforum.JavaForum.exceptions.ResourceNotFoundException;
import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String uname) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(uname.toLowerCase());
        if (user == null){
            throw new ResourceNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(), user.getPassword(), user.getAuthority());
    }
}
