package com.neim.springboot.restservices.controllers;

import com.neim.springboot.restservices.entities.Order;
import com.neim.springboot.restservices.entities.User;
import com.neim.springboot.restservices.exceptions.UserNotFoundException;
import com.neim.springboot.restservices.exceptions.UsernameNotFoundException;
import com.neim.springboot.restservices.repositories.UserRepository;
import com.neim.springboot.restservices.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public CollectionModel<User> getAllUsers() throws UsernameNotFoundException {
        List<User> allUsers = userService.getAllUsers();
        for (User user : allUsers) {
            Long userId = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);

            CollectionModel<Order> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);
            Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("orders");
            user.add(ordersLink);
        }

        Link selfLinkToUserList = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();

        return CollectionModel.of(allUsers, selfLinkToUserList);
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();
            Long userId = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);
            EntityModel<User> entityModel = EntityModel.of(user);
            return entityModel;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
