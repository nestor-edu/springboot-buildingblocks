package com.neim.springboot.restservices.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.exceptions.UserNotFoundException;
import com.neim.springboot.restservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

    @Autowired
    private UserService userService;

    // Fields with HashSet
    @GetMapping("/{id}")
    public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            Set<String> fields = new HashSet<String>();
            fields.add("id");
            fields.add("username");
            fields.add("ssn");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);

            return mapper;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // With @RequestParam
    @GetMapping("/params/{id}")
    public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long id, @RequestParam Set<String> fields) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter",
                    SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);

            return mapper;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
