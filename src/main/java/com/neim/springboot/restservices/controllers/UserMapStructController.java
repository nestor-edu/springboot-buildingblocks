package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.dtos.UserMapStructDTO;
import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.mappers.UserMapper;
import com.neim.springboot.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mapstruct/users")
public class UserMapStructController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserMapStructDTO> getAllUserDtos() {
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserMapStructDTO getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        return userMapper.userToUserDto(user);
    }
}
