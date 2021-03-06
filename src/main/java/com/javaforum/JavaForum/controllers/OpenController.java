package com.javaforum.JavaForum.controllers;

import com.javaforum.JavaForum.models.User;
import com.javaforum.JavaForum.models.UserMinimum;
import com.javaforum.JavaForum.models.UserRoles;
import com.javaforum.JavaForum.services.RoleService;
import com.javaforum.JavaForum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class OpenController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // This endpoint always anyone to create an account with the default role of USER.
    //      That role is hardcoded in this method.
    //  @param httpServletRequest - The request that comes in for creating the new user
    //  @param newminuser - A special minimum set of data that is needed to create a new user
    @PostMapping(value = "/createNewUser", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addSelf(HttpServletRequest httpServletRequest, @RequestBody UserMinimum newminuser)
            throws URISyntaxException {

        User newuser = new User();

        newuser.setUsername(newminuser.getUsername());
        newuser.setPassword(newminuser.getPassword());
        newuser.setEmail(newminuser.getEmail());

        Set<UserRoles> newRoles = new HashSet<>();
        newRoles.add(new UserRoles(newuser, roleService.findByName("user")));
        newuser.setRoles(newRoles);

        newuser = userService.save(newuser);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromUriString(httpServletRequest.getServerName() + ":" + httpServletRequest.getLocalPort() + "/users/user/{userId}")
                .buildAndExpand(newuser.getUserid()).toUri();
        responseHeaders.setLocation(newUserURI);

        RestTemplate restTemplate = new RestTemplate();
        String requestURI = "http://localhost" + ":" + httpServletRequest.getLocalPort() + "/login";

        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptableMediaTypes);
        headers.setBasicAuth(System.getenv("OAUTHCLIENTID"), System.getenv("OAUTHCLIENTSECRET"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("scope", "read write trust");
        map.add("username", newminuser.getUsername());
        map.add("password", newminuser.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String theToken = restTemplate.postForObject(requestURI, request, String.class);

        return new ResponseEntity<>(theToken, responseHeaders, HttpStatus.CREATED);
    }

    @ApiIgnore
    @GetMapping("favicon.ico")
    public void returnNoFavicon() {}
}
