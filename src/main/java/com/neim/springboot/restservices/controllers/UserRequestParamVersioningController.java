package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.dtos.UserDtoV1;
import com.neim.springboot.restservices.dtos.UserDtoV2;
import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.exceptions.UserNotFoundException;
import com.neim.springboot.restservices.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/versioning/params/users")
public class UserRequestParamVersioningController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", params = "version=1")
    public UserDtoV1 getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("USER NOT FOUND!");
        }

        User user = userOptional.get();

        return modelMapper.map(user, UserDtoV1.class);
    }

    @GetMapping(value = "/{id}", params = "version=2")
    public UserDtoV2 getUserById2(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("USER NOT FOUND!");
        }

        User user = userOptional.get();

        return modelMapper.map(user, UserDtoV2.class);
    }
}
