package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.exceptions.UserExistsException;
import com.neim.springboot.restservices.exceptions.UserNotFoundException;
import com.neim.springboot.restservices.exceptions.UsernameNotFoundException;
import com.neim.springboot.restservices.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Api(tags = "User Management RESTful Service", value = "User Controller API")
@RequestMapping("/users")
@RestController
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Retrieve list of users")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Creates a new user")
    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@ApiParam("User information for a new user to be created") @Valid @RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            return userOptional.get();
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
    @PutMapping("/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }
    @GetMapping("/search/{username}")
    public User getUserByUsername(@PathVariable("username") String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found in repository.");
        }
        return user;
    }
}
