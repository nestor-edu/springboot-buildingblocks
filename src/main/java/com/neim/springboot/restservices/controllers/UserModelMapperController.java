package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.dtos.UserDTO;
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
@RequestMapping("/modelmapper/users")
public class UserModelMapperController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("USER NOT FOUND!");
        }

        User user = userOptional.get();

        return modelMapper.map(user, UserDTO.class);
    }
}
